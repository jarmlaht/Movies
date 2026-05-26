package com.example.movies.controller;

import com.example.movies.config.SecurityConfig;
import com.example.movies.dto.DirectorSummaryDTO;
import com.example.movies.dto.MovieSummaryDTO;
import com.example.movies.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
@Import(SecurityConfig.class)
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private MovieService movieService;

    @MockitoBean
    private com.example.movies.service.CustomOAuth2UserService oAuth2UserService;

    @MockitoBean
    private org.springframework.data.mongodb.core.MongoTemplate mongoTemplate;

    @Test
    void shouldReturnHomeViewWithMovies() throws Exception {
        List<MovieSummaryDTO> summaries = List.of(
                new MovieSummaryDTO("1", "The Matrix", "A hacker discovers reality.", new DirectorSummaryDTO("Lana", "Wachowski"))
        );

        when(movieService.findAllSummaries()).thenReturn(summaries);

        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("movies"))
                .andExpect(model().attribute("movies", summaries));
    }
}
