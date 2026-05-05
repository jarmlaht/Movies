package com.example.movies.controller;

import com.example.movies.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MovieService movieService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("movies", movieService.findAllSummaries());
        return "index";
    }
}
