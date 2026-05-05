package com.example.movies.service;

import com.example.movies.dto.DirectorSummaryDTO;
import com.example.movies.dto.MovieSummaryDTO;
import com.example.movies.model.Movie;
import com.example.movies.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieServiceImpl implements MovieService {

    private final MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    @Override
    public List<MovieSummaryDTO> findAllSummaries() {
        return movieRepository.findAll().stream()
                .map(this::convertToSummaryDTO)
                .collect(Collectors.toList());
    }

    private MovieSummaryDTO convertToSummaryDTO(Movie movie) {
        return MovieSummaryDTO.builder()
                .id(movie.getId())
                .name(movie.getName())
                .synopsis(movie.getSynopsis())
                .director(DirectorSummaryDTO.builder()
                        .firstName(movie.getDirector().getFirstName())
                        .lastName(movie.getDirector().getLastName())
                        .build())
                .build();
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