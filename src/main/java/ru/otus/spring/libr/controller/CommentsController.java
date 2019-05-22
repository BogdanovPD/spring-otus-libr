package ru.otus.spring.libr.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.libr.dto.CommentDto;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.services.LibrService;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentsController {

    private final LibrService librService;

    @GetMapping(value = "/api/comments")
    public Mono<List<String>> getComments(@RequestParam String name, @RequestParam String author) {
        Flux<Book> books = librService.getBooksByNameAndAuthor(name, author);
        return books.next()
                .switchIfEmpty(Mono.empty())
                .flatMap(b -> Mono.just(b.getComments()));
    }

    @PostMapping(value = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<String>> addComments(@RequestBody CommentDto commentDto) {
        return librService.getBooksByNameAndAuthor(
                commentDto.getBookName(), commentDto.getBookAuthor()).collectList().flatMap(b -> {
            if (b.isEmpty()) {
                return Mono.just(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot find the book"));
            }
            Book book = b.get(0);
            book.getComments().add(commentDto.getComment());
            Mono<Book> bookMono = librService.saveBook(book);
            bookMono.subscribe(bb -> log.info("Comment successfully added"));
            return Mono.just(ResponseEntity.ok().body("Comment successfully added"));
        });
    }
}
