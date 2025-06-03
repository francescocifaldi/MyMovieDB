package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", categoryService.findAll());
        return "categories/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        return "categories/show";
    }

}
