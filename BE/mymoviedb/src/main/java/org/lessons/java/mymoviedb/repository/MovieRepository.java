package org.lessons.java.mymoviedb.repository;

import org.lessons.java.mymoviedb.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Integer> {

}
