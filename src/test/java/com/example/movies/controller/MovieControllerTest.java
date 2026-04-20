package com.example.movies.controller;

import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private MovieService movieService;

    static List<Movie> MOVIES = List.of(
            new Movie("1", "The Matrix", 1999, List.of("Action", "Sci-Fi"), 16, 9, null, null, "A hacker discovers reality."),
            new Movie("2", "Inception", 2010, List.of("Action", "Sci-Fi"), 16, 9, null, null, "Dreams within dreams.")
    );

    static Movie MOVIE = new Movie("3", "The Shawshank Redemption", 1994, List.of("Drama"), 18, 9, null, null, "Hope never dies.");


    @Test
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
    void shouldSaveNewMovieToDb() throws Exception {
        when(movieService.save(MOVIE)).thenReturn(MOVIE);

        mockMvc.perform(post("/movies")
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