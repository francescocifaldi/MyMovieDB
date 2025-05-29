package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "movies/index";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieRepository.findById(id).get());
        return "movies/show";
    }

}
