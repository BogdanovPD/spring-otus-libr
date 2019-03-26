package ru.otus.spring.libr.dao;

import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface BooksDao {

    void saveBook(Book book);

    Optional<Book> getBookByName(String name);
    Optional<Book> getBookById(long id);

    List<Book> getAllBooks();
    List<Book> getAllBooksByAuthor(Author author);
    List<Book> getAllBooksByGenre(Genre genre);
    List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre);
    Book loadComments(Book book);

    void deleteAllBooks();
    void deleteAllBooksByAuthor(Author author);
    void deleteAllBooksByGenre(Genre genre);

    @Transactional
    void deleteAllCommentsByBook(Book book);

    @Transactional
    void deleteAllComments();
}
