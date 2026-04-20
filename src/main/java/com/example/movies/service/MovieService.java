package com.example.movies.service;

import com.example.movies.model.Movie;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();

    Optional<Movie> findById(String id);

    Movie save(Movie movie);

    void deleteById(String id);
}