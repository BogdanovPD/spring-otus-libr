package ru.otus.spring.libr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.services.LibrService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class BooksController {

    private final LibrService librService;

    @PostMapping(value = "/api/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return ResponseEntity.ok(librService.saveBook(book));
    }

    @GetMapping(value = "/api/books")
    public List<Book> getAllBooks() {
        return librService.getAllBooks();
    }

}
