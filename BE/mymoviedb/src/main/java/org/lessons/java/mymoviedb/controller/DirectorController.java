package org.lessons.java.mymoviedb.controller;

import java.io.IOException;
import org.lessons.java.mymoviedb.model.Director;
import org.lessons.java.mymoviedb.service.DirectorService;
import org.lessons.java.mymoviedb.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/directors")
public class DirectorController {
    @Autowired
    private DirectorService directorService;

    @Autowired
    private FileUploadService fileUploadService;

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
            @RequestParam("imageFile") MultipartFile imageFile, Model model) {

        if (bindingResult.hasErrors()) {
            return "directors/create-or-edit";
        }

        Director savedDirector = directorService.create(director);

        if (!imageFile.isEmpty()) {
            try {
                String fileName = fileUploadService.saveImage(imageFile, savedDirector.getId() + "_" +
                        savedDirector.getName() + savedDirector.getSurname(), "directors");
                savedDirector.setPhoto(fileName);
                directorService.update(savedDirector);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/directors";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("director", directorService.getById(id));
        model.addAttribute("edit", true);
        return "directors/create-or-edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @Valid @ModelAttribute("director") Director director,
            BindingResult bindingResult, @PathVariable Integer id,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            return "directors/create-or-edit";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = fileUploadService.saveImage(imageFile,
                        director.getId() + "_" +
                                director.getName() + director.getSurname(),
                        "directors");
                director.setPhoto(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        director.setId(id);
        directorService.create(director);
        return "redirect:/directors/" + director.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        directorService.deleteById(id);
        return "redirect:/directors";
    }
}
