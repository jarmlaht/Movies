package com.example.movies;

import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class MovieDataLoader implements CommandLineRunner {

    private final MovieRepository movieRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        if (movieRepository.count() > 0) {
            return;
        }

        ClassPathResource resource = new ClassPathResource("movies-compact.json");

        try (InputStream inputStream = resource.getInputStream()) {
            List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<>() {});
            log.debug("Loaded {} movies", movies.size());
            //movieRepository.saveAll(movies);
        }
    }
}