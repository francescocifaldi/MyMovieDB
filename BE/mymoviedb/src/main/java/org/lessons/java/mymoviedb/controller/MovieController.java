package org.lessons.java.mymoviedb.controller;

import java.io.IOException;
import org.springframework.data.domain.Page;
import org.lessons.java.mymoviedb.model.Movie;
import org.lessons.java.mymoviedb.service.CategoryService;
import org.lessons.java.mymoviedb.service.DirectorService;
import org.lessons.java.mymoviedb.service.FileUploadService;
import org.lessons.java.mymoviedb.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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

@Controller
@RequestMapping("/movies")
public class MovieController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private DirectorService directorService;

    @Autowired
    private FileUploadService fileUploadService;

    @GetMapping
    public String index(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Movie> moviesPage = movieService.findAll(PageRequest.of(page, size, Sort.by("title").ascending()));

        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("movies", moviesPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviesPage.getTotalPages());
        model.addAttribute("pageSize", size);
        model.addAttribute("totalElements", moviesPage.getTotalElements());
        model.addAttribute("numberOfElements", moviesPage.getNumberOfElements());

        return "movies/index";
    }

    @GetMapping("/search")
    public String searchMovies(
            @RequestParam("q") String query,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {

        Page<Movie> moviesPage = movieService.search(query, PageRequest.of(page, size, Sort.by("title").ascending()));
        model.addAttribute("moviesPage", moviesPage);
        model.addAttribute("movies", moviesPage.getContent());
        model.addAttribute("query", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", moviesPage.getTotalPages());
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

        Movie savedMovie = movieService.create(movie);

        if (!imageFile.isEmpty()) {
            try {
                String fileName = fileUploadService.saveImage(imageFile,
                        savedMovie.getId() + "_" + savedMovie.getTitle(), "movies");
                savedMovie.setCoverImage(fileName);
                movieService.update(savedMovie);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }

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
            BindingResult bindingResult, @PathVariable Integer id,
            @RequestParam("imageFile") MultipartFile imageFile,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("directors", directorService.findAll());
            return "movies/create-or-edit";
        }

        if (!imageFile.isEmpty()) {
            try {
                String fileName = fileUploadService.saveImage(imageFile, movie.getId() + "_" + movie.getTitle(),
                        "movies");
                movie.setCoverImage(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        movie.setId(id);
        movieService.create(movie);
        return "redirect:/movies/" + movie.getId();
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Integer id) {
        Movie movie = movieService.getById(id);
        if (movie != null) {
            movieService.deleteById(id);
        }
        return "redirect:/movies";
    }
}
