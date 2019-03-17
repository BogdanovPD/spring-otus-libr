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

import static ru.otus.spring.libr.util.DaoUtil.getNamedEntityMap;

@Repository
@RequiredArgsConstructor
public class BooksDaoJdbc implements BooksDao {

    private final NamedParameterJdbcOperations jdbc;
    private final BookMapper bookMapper;

    @Override
    public void saveBook(Book book) {
        Map<String, Object> parameters = getNamedEntityMap(book);
        parameters.put("author_id", book.getAuthor().getId());
        parameters.put("genre_id", book.getGenre().getId());
        jdbc.update("insert into books values (default, :name, :author_id, :genre_id)", parameters);
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select * from books where name = :name", parameters, bookMapper));
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
                    "select * from books where id = :id", parameters, bookMapper));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Book> getAllBooks() {
        try {
            return jdbc.query("select * from books", bookMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("author_id", author.getId());
        try {
            return jdbc.query("select * from books where author_id = :author_id", parameters, bookMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByGenre(Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("genre_id", genre.getId());
        try {
            return jdbc.query("select * from books where genre_id = :genre_id", parameters, bookMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public List<Book> getAllBooksByAuthorAndGenre(Author author, Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("author_id", author.getId());
        parameters.put("genre_id", genre.getId());
        try {
            return jdbc.query("select * from books where author_id = :author_id " +
                    "and genre_id = :genre_id", parameters, bookMapper);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllBooks() {
        jdbc.update("delete from genres", new HashMap<>());
    }

    @Override
    public void deleteAllBooksByAuthor(Author author) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("author_id", author.getId());
        jdbc.update("delete from books where author_id = :author_id", parameters);
    }

    @Override
    public void deleteAllBooksByGenre(Genre genre) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("genre_id", genre.getId());
        jdbc.update("delete from books where genre_id = :genre_id", parameters);
    }
}
