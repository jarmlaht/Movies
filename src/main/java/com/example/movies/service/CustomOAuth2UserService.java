package com.example.movies.service;

import com.example.movies.model.User;
import com.example.movies.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String provider = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();
        
        String providerId;
        String email;
        String name;

        if ("github".equals(provider)) {
            providerId = String.valueOf(attributes.get("id"));
            email = (String) attributes.get("email");
            name = (String) attributes.get("login");
        } else if ("google".equals(provider)) {
            providerId = (String) attributes.get("sub");
            email = (String) attributes.get("email");
            name = (String) attributes.get("name");
        } else {
            throw new OAuth2AuthenticationException("Unknown provider: " + provider);
        }

        updateOrCreateUser(provider, providerId, email, name);

        return oAuth2User;
    }

    private void updateOrCreateUser(String provider, String providerId, String email, String name) {
        User user = userRepository.findByProviderAndProviderId(provider, providerId)
                .orElseGet(() -> User.builder()
                        .provider(provider)
                        .providerId(providerId)
                        .roles(Collections.singleton("ROLE_USER"))
                        .build());

        user.setEmail(email);
        user.setName(name);
        userRepository.save(user);
    }
}
