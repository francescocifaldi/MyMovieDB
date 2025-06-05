package org.lessons.java.mymoviedb.controller.api;

import java.util.List;

import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/movies")
public class MovieRestController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.findAll();
    }

}
