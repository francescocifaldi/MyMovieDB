package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.model.Director;
import org.lessons.java.mymoviedb.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("directors", directorService.findAll());
        return "directors/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("director", directorService.getById(id));
        return "directors/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("director", new Director());
        return "directors/create-or-edit";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("director") Director director, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "directors/create-or-edit";
        }

        directorService.create(director);
        return "redirect:/directors";
    }

}
