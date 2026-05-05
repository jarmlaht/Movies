package com.example.movies.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieSummaryDTO {
    private String id;
    private String name;
    private String synopsis;
    private DirectorSummaryDTO director;
}
