package org.lessons.java.mymoviedb.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public List<Movie> findAll() {
        return movieRepository.findAll();
    }

    public Page<Movie> findAll(Pageable pageable) {
        return movieRepository.findAll(pageable);
    }

    public List<Movie> search(String query) {
        return movieRepository
                .findDistinctByTitleContainingIgnoreCaseOrDirector_NameContainingIgnoreCaseOrDirector_surnameContainingIgnoreCaseOrCategories_NameContainingIgnoreCase(
                        query, query, query, query);
    }

    public Movie getById(Integer id) {
        return movieRepository.findById(id).get();
    }

    public Movie create(Movie movie) {
        return movieRepository.save(movie);
    }

    public Movie update(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteById(Integer id) {
        movieRepository.deleteById(id);
    }
}
