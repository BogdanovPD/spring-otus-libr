package ru.otus.spring.libr.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.otus.spring.libr.entities.Book;

@Controller
public class LibrController {

    @GetMapping("/")
    public String main(Model model) {
        model.addAttribute("book", new Book());
        return "librSpa";
    }

}
