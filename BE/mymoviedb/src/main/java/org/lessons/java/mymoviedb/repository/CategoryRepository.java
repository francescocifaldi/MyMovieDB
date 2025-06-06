package org.lessons.java.mymoviedb.repository;

import org.lessons.java.mymoviedb.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {

    boolean existsByNameIgnoreCase(String name);

}
