package ru.otus.spring.libr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.services.LibrService;

@RestController
@RequiredArgsConstructor
public class BooksController {

    private final LibrService librService;

    @PostMapping(value = "/api/books", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Book> saveBook(@RequestBody Book book) {
        return librService.newBook(book.getName(), book.getGenre(), book.getAuthor());
    }

    @GetMapping(value = "/api/books")
    public Flux<Book> getAllBooks() {
        return librService.getAllBooks();
    }

}
