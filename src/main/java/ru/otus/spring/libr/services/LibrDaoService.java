package ru.otus.spring.libr.services;

import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface LibrDaoService {

    Optional<Author> saveAuthor(String author);
    Optional<Author> getAuthorByName(String name);
    List<Author> getAllAuthors();
    Optional<Genre> saveGenre(String genre);
    Optional<Genre> getGenreByName(String name);
    List<Genre> getAllGenres();
    Optional<Book> getBookByName(String name);
    List<Book> getAllBooks();
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByAuthorAndGenre(String author, String genre);
    Optional<Book> saveBook(String name, Author author, Genre genre);

}
