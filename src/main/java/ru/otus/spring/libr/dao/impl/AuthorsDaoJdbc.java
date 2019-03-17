package ru.otus.spring.libr.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.libr.dao.AuthorsDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.mappers.AuthorMapper;

import java.util.*;

import static ru.otus.spring.libr.util.DaoUtil.getNamedEntityMap;

@Repository
@RequiredArgsConstructor
public class AuthorsDaoJdbc implements AuthorsDao {

    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void saveAuthor(Author author) {
        Map<String, Object> parameters = getNamedEntityMap(author);
        jdbc.update("insert into authors(name) values (:name)", parameters);
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select * from authors where name = :name", parameters, new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Author> getAuthorById(long id) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select * from authors where id = :id", parameters, new AuthorMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Author> getAllAuthors() {
        try {
            return jdbc.query("select * from authors", new AuthorMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllAuthors() {
        jdbc.update("delete from genres", new HashMap<>());
    }
}
