package org.lessons.java.mymoviedb.service;

import java.util.List;
import java.util.Optional;
import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Movie getById(Integer id) {
        return movieRepository.findById(id).get();
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }
}
