package ru.otus.spring.libr.dao;

import ru.otus.spring.libr.entities.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorsDao {

    void saveAuthor(Author author);
    Optional<Author> getAuthorByName(String name);
    Optional<Author> getAuthorById(long id);
    List<Author> getAllAuthors();
    void deleteAllAuthors();

}
