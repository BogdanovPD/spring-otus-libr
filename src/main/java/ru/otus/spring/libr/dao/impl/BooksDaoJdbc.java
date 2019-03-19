package ru.otus.spring.libr.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.libr.dao.BooksDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.mappers.BookMapper;

import java.util.*;

import static ru.otus.spring.libr.util.DaoUtil.*;
import static ru.otus.spring.libr.util.DaoUtil.AUTHOR_ID;

@Repository
@RequiredArgsConstructor
public class BooksDaoJdbc implements BooksDao {

    private final NamedParameterJdbcOperations jdbc;
    private static final BookMapper BOOK_MAPPER = new BookMapper();

    @Override
    public void saveBook(Book book) {
        Map<String, Object> parameters = getNamedEntityMap(book);
        parameters.put(AUTHOR_ID, book.getAuthor().getId());
        parameters.put(GENRE_ID, book.getGenre().getId());
        jdbc.update("insert into books values (default, :name, :author_id, :genre_id)", parameters);
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    getSelectAllBooksQueryBase() +
                            "where b.name = :name", parameters, BOOK_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Book> getBookById(long id) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    getSelectAllBooksQueryBase() +
                    "where b.id = :id", parameters, BOOK_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return jdbc.query(getSelectAllBooksQueryBase(), BOOK_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put(AUTHOR_ID, author.getId());
        try {
            return jdbc.query(
                    getSelectAllBooksQueryBase() +
                    "where a.id = :author_id", parameters, BOOK_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByGenre(Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put(GENRE_ID, genre.getId());
        try {
            return jdbc.query(
                    getSelectAllBooksQueryBase() +
                    "where g.id = :genre_id", parameters, BOOK_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put(AUTHOR_ID, author.getId());
        parameters.put(GENRE_ID, genre.getId());
        try {
            return jdbc.query(getSelectAllBooksQueryBase() +
                    "where a.id = :author_id " +
                    "and b.id = :genre_id", parameters, BOOK_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllBooks() {
        jdbc.update("delete from books", new HashMap<>());
    }

    @Override
    public void deleteAllBooksByAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put(AUTHOR_ID, author.getId());
        jdbc.update("delete from books where author_id = :author_id", parameters);
    }

    @Override
    public void deleteAllBooksByGenre(Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put(GENRE_ID, genre.getId());
        jdbc.update("delete from books where genre_id = :genre_id", parameters);
    }

    private String getSelectAllBooksQueryBase() {
        return "select b.id as " + BOOK_ID + ",\n" +
                "       b.name as " + BOOK_NAME + ",\n" +
                "       a.id as " + AUTHOR_ID + ",\n" +
                "       a.name as " + AUTHOR_NAME + ",\n" +
                "       g.id as " + GENRE_ID + ",\n" +
                "       g.name as " + GENRE_NAME + " from books as b\n" +
                "inner join authors as a on b.author_id = a.id\n" +
                "inner join genres as g on b.genre_id = g.id\n";
    }
}
