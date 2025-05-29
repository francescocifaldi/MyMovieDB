package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.service.CategoryService;
import org.lessons.java.mymoviedb.service.DirectorService;
import org.lessons.java.mymoviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "movies/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getById(id));
        return "movies/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("directors", directorService.findAll());
        return "movies/create-or-edit";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("movie") Movie movie,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "movies/create-or-edit";
        }

        movieService.create(movie);
        return "redirect:/movies";
    }

}
