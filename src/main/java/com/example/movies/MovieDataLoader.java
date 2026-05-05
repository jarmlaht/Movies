package com.example.movies;

import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;
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
    public void run(String... args) {
        if (movieRepository.count() > 0) {
            log.debug("Database already populated: {}", movieRepository.count());
            return;
        }

        ClassPathResource resource = new ClassPathResource("movies-compact.json");

        try (InputStream inputStream = resource.getInputStream()) {
            List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<>() {});
            movieRepository.saveAll(movies);
            log.info("Loaded {} movies to the database!", movies.size());
        } catch (Exception e) {
            log.error("Failed to load or save movies!", e);
        }
    }
}