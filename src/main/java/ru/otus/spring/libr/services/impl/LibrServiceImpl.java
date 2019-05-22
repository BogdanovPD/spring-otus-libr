package ru.otus.spring.libr.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.services.LibrService;

import java.util.ArrayList;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibrServiceImpl implements LibrService {

    private final BookRepository bookRepository;

    @Override
    public Flux<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Mono<Book> saveBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Mono<Book> newBook(String name, String genre, String author) {
        return bookRepository.findAllByNameAndAuthor(name, author).collectList().flatMap(b -> {
            if (b.isEmpty()) {
                return bookRepository.save(Book.builder()
                        .name(name)
                        .author(author)
                        .genre(genre)
                        .comments(new ArrayList<>())
                        .build());
            }
            log.error("Described book already exists");
            return Mono.empty();
        });
    }


    @Override
    public Flux<Book> getBooksByName(String name) {
        return bookRepository.findAllByName(name);
    }

    @Override
    public Flux<Book> getBooksByAuthor(String author) {
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public Flux<Book> getBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public Flux<Book> getBooksByNameAndAuthor(String name, String author) {
        return bookRepository.findAllByNameAndAuthor(name, author);
    }

    @Override
    public Flux<Book> getBooksByNameAndGenre(String name, String genre) {
        return bookRepository.findAllByNameAndGenre(name, genre);
    }

    @Override
    public Flux<Book> getBooksByAuthorAndGenre(String author, String genre) {
        return bookRepository.findAllByAuthorAndGenre(author, genre);
    }

    @Override
    public Flux<Book> getBooksByNameAndAuthorAndGenre(String name, String author, String genre) {
        return bookRepository.findAllByNameAndAuthorAndGenre(name, author, genre);
    }

}


