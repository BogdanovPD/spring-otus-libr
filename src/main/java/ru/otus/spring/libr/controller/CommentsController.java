package ru.otus.spring.libr.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.libr.dto.CommentDto;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.services.LibrService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final LibrService librService;

    @GetMapping(value = "/api/comments")
    public List<String> getComments(@RequestParam String name, @RequestParam String author) {
        List<Book> books = librService.getBooksByNameAndAuthor(name, author);
        if (books.isEmpty()) {
            return Collections.emptyList();
        }
        return librService.getCommentsByBook(books.get(0)).stream().map(c -> c.getText()).collect(Collectors.toList());
    }

    @PostMapping(value = "/api/comments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> addComments(@RequestBody CommentDto commentDto) {
        List<Book> books = librService.getBooksByNameAndAuthor(commentDto.getBookName(), commentDto.getBookAuthor());
        if (books.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cannot found the book");
        }
        Book book = books.get(0);
        librService.addComment(book, Comment.builder().text(commentDto.getComment()).book(book).build());
        return ResponseEntity.ok("Comment successfully added!");
    }
}
