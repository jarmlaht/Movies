package com.example.movies.controller;

import com.example.movies.dto.MovieSummaryDTO;
import com.example.movies.model.Movie;
import com.example.movies.service.MovieService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {
    private final MovieService movieService;

    @GetMapping
    public List<Movie> findAll() {
        return movieService.findAll();
    }

    @GetMapping("/summaries")
    public List<MovieSummaryDTO> findAllSummaries() {
        return movieService.findAllSummaries();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> findById(@PathVariable String id) {
        return movieService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Movie save(@Valid @RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Movie> update(@Valid @PathVariable String id, @RequestBody Movie movie) {
        return movieService.findById(id)
                .map(existingMovie -> {
                    movie.setId(id);
                    return ResponseEntity.ok(movieService.save(movie));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id) {
        if (movieService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        movieService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Movie> search(@RequestParam String query) {
        return movieService.searchByName(query);
    }
}