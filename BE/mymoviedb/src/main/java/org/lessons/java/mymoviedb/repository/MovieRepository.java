package org.lessons.java.mymoviedb.repository;

import org.lessons.java.mymoviedb.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
    Page<Movie> findDistinctByTitleContainingIgnoreCaseOrDirector_NameContainingIgnoreCaseOrDirector_surnameContainingIgnoreCaseOrCategories_NameContainingIgnoreCase(
            String title, String directorName, String directorSurname, String categoryName, Pageable pageable);
}
