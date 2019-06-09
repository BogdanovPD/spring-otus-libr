package ru.otus.spring.libr.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends CrudRepository<Author, Long> {

    Optional<Author> getAuthorByName(String name);
    List<Author> findAll();

}