package com.example.movies.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "movies")
public class Movie {
    @Id
    private String id;
    @NotBlank(message = "Name is required")
    @Size(max = 200, message = "Name must be at most 200 characters")
    private String name;

    @NotNull(message = "Year is required")
    @Min(value = 1888, message = "Year must be realistic")
    @Max(value = 2100, message = "Year must be realistic")
    private Integer year;

    @NotEmpty(message = "At least one genre is required")
    private List<@NotBlank(message = "Genre cannot be blank") String> genres;

    @NotNull(message = "Age limit is required")
    @Min(value = 0, message = "Age limit cannot be negative")
    @Max(value = 21, message = "Age limit is too high")
    private Integer ageLimit;

    @NotNull(message = "Rating is required")
    @Min(value = 0, message = "Rating must be at least 0")
    @Max(value = 10, message = "Rating must be at most 10")
    private Integer rating;

    @NotEmpty(message = "At least one actor is required")
    @Valid
    private List<Actor> actors;

    @NotNull(message = "Director is required")
    @Valid
    private Director director;

    @Size(max = 5000, message = "Synopsis must be at most 5000 characters")
    private String synopsis;
}