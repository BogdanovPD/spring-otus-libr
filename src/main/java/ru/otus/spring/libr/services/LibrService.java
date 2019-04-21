package ru.otus.spring.libr.services;

import ru.otus.spring.libr.entities.Book;

import java.util.List;
import java.util.Optional;

public interface LibrService {

    Optional<Book> newBook(String name, String genre, String author);
    List<Book> getAllBooks();
    List<Book> getBooksByName(String name);
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByNameAndAuthor(String name, String author);
    List<Book> getBooksByNameAndGenre(String name, String genre);
    List<Book> getBooksByAuthorAndGenre(String author, String genre);
    List<Book> getBooksByNameAndAuthorAndGenre(String name, String author, String genre);
    Book saveBook(Book book);
}
