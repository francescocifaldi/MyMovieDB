package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.model.Category;
import org.lessons.java.mymoviedb.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        categoryService.deleteById(id);
        return "redirect:/categories";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create-or-edit";
    }

    @PostMapping("/create")
    public String createCategory(@Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult,
            Model model) {

        if (categoryService.categoryExists(category.getName())) {
            bindingResult.rejectValue("name", "error.category", "Questo nome esiste già.");
        }

        if (bindingResult.hasErrors()) {
            return "categories/create-or-edit";
        }

        categoryService.create(category);
        return "redirect:/categories"; //
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("category", categoryService.getById(id));
        model.addAttribute("edit", true);
        return "categories/create-or-edit";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, @Valid @ModelAttribute("category") Category category,
            BindingResult bindingResult, Model model) {

        if (categoryService.categoryExists(category.getName())) {
            bindingResult.rejectValue("name", "error.category", "Questo nome esiste già.");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("edit", true);
            return "categories/create-or-edit";
        }

        category.setId(id);
        categoryService.create(category);
        return "redirect:/categories";
    }

}
