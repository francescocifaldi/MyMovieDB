package org.lessons.java.mymoviedb.service;

import java.util.List;

import org.lessons.java.mymoviedb.model.Category;
import org.lessons.java.mymoviedb.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    public Category getById(Integer id) {
        return categoryRepository.findById(id).get();
    }

    public void deleteById(Integer id) {
        categoryRepository.deleteById(id);
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public boolean categoryExists(String name) {
        return categoryRepository.existsByNameIgnoreCase(name);
    }
}
