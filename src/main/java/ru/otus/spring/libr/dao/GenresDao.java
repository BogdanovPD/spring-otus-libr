package ru.otus.spring.libr.dao;

import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenresDao {

    void saveGenre(Genre genre);
    Optional<Genre> getGenreByName(String name);
    Optional<Genre> getGenreById(long id);
    List<Genre> getAllGenres();
    void deleteAllGenres();

}
