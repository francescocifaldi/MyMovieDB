package org.lessons.java.mymoviedb.service;

import java.util.List;

import org.lessons.java.mymoviedb.model.Category;
import org.lessons.java.mymoviedb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }
}
