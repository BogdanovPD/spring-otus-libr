package ru.otus.spring.libr.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository extends CrudRepository<Genre, Long> {

    Optional<Genre> getGenreByName(String name);
    List<Genre> findAll();

}
