package org.lessons.java.mymoviedb.repository;

import java.util.List;

import org.lessons.java.mymoviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findDistinctByTitleContainingIgnoreCaseOrDirector_NameContainingIgnoreCaseOrDirector_surnameContainingIgnoreCaseOrCategories_NameContainingIgnoreCase(
            String title, String directorName, String directorSurname, String categoryName);
}
