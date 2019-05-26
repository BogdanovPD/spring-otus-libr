package ru.otus.spring.libr.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.spring.libr.entities.Book;

public interface LibrService {
        Flux<Book> getAllBooks();
        Mono<Book> newBook(String name, String genre, String author);
        Flux<Book> getBooksByName(String name);
        Flux<Book> getBooksByAuthor(String author);
        Flux<Book> getBooksByGenre(String genre);
        Flux<Book> getBooksByNameAndAuthor(String name, String author);
        Flux<Book> getBooksByNameAndGenre(String name, String genre);
        Flux<Book> getBooksByAuthorAndGenre(String author, String genre);
        Flux<Book> getBooksByNameAndAuthorAndGenre(String name, String author, String genre);
        Mono<Book> saveBook(Book book);
}
