package com.example.movies.model;

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
    private String name;
    private Integer year;
    private List<String> genres;
    private Integer ageLimit;
    private Integer rating;
    private List<Actor> actors;
    private Director director;
    private String synopsis;
}