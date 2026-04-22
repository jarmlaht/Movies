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

import java.io.IOException;
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
            log.info("Movies already loaded to the database!");
            return;
        }

        ClassPathResource resource = new ClassPathResource("movies-compact.json");

        try (InputStream inputStream = resource.getInputStream()) {
            List<Movie> movies = objectMapper.readValue(inputStream, new TypeReference<>() {});
            movieRepository.saveAll(movies);
            log.info("Loaded {} movies to the database!", movies.size());
        } catch (IOException e) {
            log.error("Failed to load movies from JSON file!", e);
        } catch (IllegalArgumentException e) {
            log.error("Saving movies failed!", e);
        } catch (Exception e) {
            log.error("Unexpected error while loading and saving movies!", e);
        }
    }
}