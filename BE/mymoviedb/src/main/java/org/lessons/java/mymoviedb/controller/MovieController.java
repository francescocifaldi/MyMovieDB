package org.lessons.java.mymoviedb.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.service.CategoryService;
import org.lessons.java.mymoviedb.service.DirectorService;
import org.lessons.java.mymoviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DirectorService directorService;

    @GetMapping
    public String index(Model model) {
        model.addAttribute("movies", movieService.findAll());
        return "movies/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable Integer id, Model model) {
        model.addAttribute("movie", movieService.getById(id));
        return "movies/show";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("movie", new Movie());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("directors", directorService.findAll());
        return "movies/create-or-edit";
    }

    @PostMapping("/create")
    public String store(
            @Valid @ModelAttribute("movie") Movie movie,
            BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("directors", directorService.findAll());
            return "movies/create-or-edit";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = saveImageToFileSystem(imageFile, movie.getTitle());
                movie.setCoverImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        movieService.create(movie);
        return "redirect:/movies";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable Integer id, Model model) {
        Movie movie = movieService.getById(id);
        model.addAttribute("movie", movie);
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("directors", directorService.findAll());
        model.addAttribute("edit", true);
        return "movies/create-or-edit";
    }

    @PostMapping("/{id}/edit")
    public String update(
            @Valid @ModelAttribute("movie") Movie movie,
            BindingResult bindingResult,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("directors", directorService.findAll());
            return "movies/create-or-edit";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = saveImageToFileSystem(imageFile, movie.getTitle());
                movie.setCoverImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        movieService.create(movie);
        return "redirect:/movies";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        Movie movie = movieService.getById(id);
        if (movie != null) {
            movieService.deleteById(id);
        }
        return "redirect:/movies";
    }

    private String saveImageToFileSystem(MultipartFile imageFile, String title) throws IOException {
        String extension = Optional.ofNullable(imageFile.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(imageFile.getOriginalFilename().lastIndexOf(".")))
                .orElse("");

        String fileName = title + extension;

        // Percorso relativo al progetto (dentro static/uploads)
        Path uploadDir = Paths.get("be/mymoviedb/src/main/resources/static/uploads");

        // Crea la cartella se non esiste
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        Path filePath = uploadDir.resolve(fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return fileName;
    }

}
