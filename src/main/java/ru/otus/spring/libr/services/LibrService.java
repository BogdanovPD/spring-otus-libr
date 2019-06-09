package ru.otus.spring.libr.services;

import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface LibrService {

    void saveAuthor(Author author);
    Optional<Author> getAuthorByName(String name);
    List<Author> getAllAuthors();
    void saveGenre(Genre genre);
    Optional<Genre> getGenreByName(String name);
    List<Genre> getAllGenres();
    Optional<Book> getBookByName(String name);
    List<Book> getAllBooks();
    List<Book> getBooksByAuthor(String author);
    List<Book> getBooksByGenre(String genre);
    List<Book> getBooksByAuthorAndGenre(String author, String genre);
    Book saveBook(Book book);
    void addComment(Book book, Comment comment);
    List<Comment> getCommentsByBook(Book book);

    List<Book> getBooksByNameAndAuthor(String name, String author);
}
