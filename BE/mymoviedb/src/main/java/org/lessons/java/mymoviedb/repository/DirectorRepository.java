package org.lessons.java.mymoviedb.repository;

import org.lessons.java.mymoviedb.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer> {

}
