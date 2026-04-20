package com.example.movies.service;

import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public Optional<Movie> findById(String id) {
        return movieRepository.findById(id);
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    @Override
    public void deleteById(String id) {
        movieRepository.deleteById(id);
    }

    @Override
    public List<Movie> searchByName(String query) {
        return movieRepository.findByNameContainingIgnoreCase(query);
    }
}