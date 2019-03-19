package ru.otus.spring.libr.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.otus.spring.libr.dao.GenresDao;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.mappers.GenreMapper;

import java.util.*;

import static ru.otus.spring.libr.util.DaoUtil.getNamedEntityMap;

@Repository
@RequiredArgsConstructor
public class GenresDaoJdbc implements GenresDao {

    public static final GenreMapper GENRE_MAPPER = new GenreMapper();
    private final NamedParameterJdbcOperations jdbc;

    @Override
    public void saveGenre(Genre genre) {
        Map<String, Object> parameters = getNamedEntityMap(genre);
        jdbc.update("insert into genres(name) values (:name)", parameters);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("name", name);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select * from genres where name = :name", parameters, GENRE_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Genre> getGenreById(long id) {
        Map<String, Object> parameters = new HashMap<>(1);
        parameters.put("id", id);
        try {
            return Optional.ofNullable(jdbc.queryForObject(
                    "select * from genres where id = :id", parameters, new GenreMapper()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Genre> getAllGenres() {
        try {
            return jdbc.query("select * from genres", new GenreMapper());
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteAllGenres() {
        jdbc.update("delete from genres", new HashMap<>());
    }
}
