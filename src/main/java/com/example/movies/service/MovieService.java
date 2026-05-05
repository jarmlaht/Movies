package com.example.movies.service;

import com.example.movies.dto.MovieSummaryDTO;
import com.example.movies.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    List<MovieSummaryDTO> findAllSummaries();

    Optional<Movie> findById(String id);

    Movie save(Movie movie);

    void deleteById(String id);

    List<Movie> searchByName(String query);
}