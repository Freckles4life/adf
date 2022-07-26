package com.movie_list.controller;

import com.movie_list.service.MovieRecordService;
import com.movie_list.model.Movie;
import com.movie_list.model.MovieSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Controller
@RequestMapping(value = "/movies", produces = APPLICATION_JSON_VALUE)
public class MovieController {

    private final MovieRecordService movieRecordService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("pageName", "Hello! Welcome to movie library!");
        model.addAttribute("movies", movieRecordService.getAll());
        return "index";
    }

    @GetMapping(value = "/search")
    public String search1(MovieSearch movieSearch, Model model) {
        model.addAttribute("pageName", "Movie Search");
        return "search1";
    }

    @PostMapping(value = "/search")
    public String getSearchedMovie(@Valid MovieSearch movieSearch, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "search1";
        }
        List<Movie> movies = movieRecordService.search(movieSearch);
        model.addAttribute("movies", movies);
        return "result";
    }

    @GetMapping("/movie-add")
    public String signUp(Model map, Movie movie) {
        map.addAttribute("pageName", "add new movie");

        return "movie-add";
    }

    @GetMapping("/delete/{id}")
    public String deleteById(@PathVariable("id") Long id, Model model) {
       movieRecordService.delete(id);
        return "redirect:/movies";
    }

    @GetMapping("/edit/{id}")
    public String editById(@PathVariable("id") Long id, Model model) {
        model.addAttribute("pageName", "edit movie");

        Movie movie = movieRecordService.getById(id);
        model.addAttribute("movie", movie);

        return "movie-edit";
    }

    @PostMapping
    public String register(@Valid Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "movie-add";
        }

        movieRecordService.register(movie);

        return "redirect:/movies";
    }

    @PostMapping("/update/{id}")
    public String updateMovie(@PathVariable("id") Long id, @Valid Movie movie, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "movie-edit";
        }

       movieRecordService.updateMovie(id, movie);

        return "redirect:/movies";
    }
}