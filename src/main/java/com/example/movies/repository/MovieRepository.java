package com.example.movies.repository;

import com.example.movies.model.Movie;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MovieRepository extends MongoRepository<Movie, String> {
    List<Movie> findByNameContainingIgnoreCase(String query);
}