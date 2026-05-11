package com.example.movies.repository;

import com.example.movies.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByProviderAndProviderId(String provider, String providerId);
    Optional<User> findByEmail(String email);
}
