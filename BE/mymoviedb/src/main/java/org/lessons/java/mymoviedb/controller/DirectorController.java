package org.lessons.java.mymoviedb.controller;

import org.lessons.java.mymoviedb.service.DirectorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

}
