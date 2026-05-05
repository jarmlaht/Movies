package com.example.movies.controller;

import com.example.movies.config.SecurityConfig;
import com.example.movies.dto.DirectorSummaryDTO;
import com.example.movies.dto.MovieSummaryDTO;
import com.example.movies.model.Actor;
import com.example.movies.model.Director;
import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;
import tools.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
@Import(SecurityConfig.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieService movieService;

    static List<Movie> MOVIES = List.of(
            new Movie("1", "The Matrix", 1999, List.of("Action", "Sci-Fi"), 16, 9, 
                    List.of(new Actor("Keanu", "Reeves")), new Director("Lana", "Wachowski"), "A hacker discovers reality."),
            new Movie("2", "Inception", 2010, List.of("Action", "Sci-Fi"), 16, 9, 
                    List.of(new Actor("Leonardo", "DiCaprio")), new Director("Christopher", "Nolan"), "Dreams within dreams.")
    );

    static Movie MOVIE = new Movie("3", "The Shawshank Redemption", 1994, List.of("Drama"), 18, 9, 
            List.of(new Actor("Tim", "Robbins")), new Director("Frank", "Darabont"), "Hope never dies.");

    static List<MovieSummaryDTO> SUMMARIES = List.of(
            new MovieSummaryDTO("1", "The Matrix", "A hacker discovers reality.", new DirectorSummaryDTO("Lana", "Wachowski")),
            new MovieSummaryDTO("2", "Inception", "Dreams within dreams.", new DirectorSummaryDTO("Christopher", "Nolan"))
    );


    @Test
    @WithMockUser
    void shouldReturnAllMovies() throws Exception {
        when(movieService.findAll()).thenReturn(MOVIES);

        mockMvc.perform(get("/movies"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("The Matrix"))
                .andExpect(jsonPath("$[1].name").value("Inception"));

        verify(movieService).findAll();
    }

    @Test
    @WithMockUser
    void shouldReturnAllSummaries() throws Exception {
        when(movieService.findAllSummaries()).thenReturn(SUMMARIES);

        mockMvc.perform(get("/movies/summaries"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("The Matrix"))
                .andExpect(jsonPath("$[0].director.firstName").value("Lana"))
                .andExpect(jsonPath("$[1].name").value("Inception"))
                .andExpect(jsonPath("$[1].director.firstName").value("Christopher"));

        verify(movieService).findAllSummaries();
    }

    @Test
    @WithMockUser
    void shouldSearchMoviesByQuery() throws Exception {
        when(movieService.searchByName("matrix")).thenReturn(Collections.singletonList(MOVIES.get(0)));

        mockMvc.perform(get("/movies/search").param("query", "matrix"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].name").value("The Matrix"));

        verify(movieService).searchByName("matrix");
    }

    @Test
    @WithMockUser
    void shouldSaveNewMovieToDb() throws Exception {
        when(movieService.save(MOVIE)).thenReturn(MOVIE);

        mockMvc.perform(post("/movies")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(MOVIE)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value("3"))
                .andExpect(jsonPath("$.name").value("The Shawshank Redemption"))
                .andExpect(jsonPath("$.year").value(1994))
                .andExpect(jsonPath("$.synopsis").value("Hope never dies."));

        verify(movieService).save(MOVIE);
    }
}