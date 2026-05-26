package com.example.movies;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootApplication
public class MoviesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoviesApplication.class, args);
	}

	@Bean
    CommandLineRunner logMongoDatabase(MongoTemplate mongoTemplate) {
		return args -> {
			try {
				if (mongoTemplate != null && mongoTemplate.getDb() != null) {
					System.out.println("Mongo database in use: " + mongoTemplate.getDb().getName());
				}
			} catch (Exception ignored) {
			}
		};
	}
}
