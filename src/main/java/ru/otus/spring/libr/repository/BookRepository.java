package ru.otus.spring.libr.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends CrudRepository<Book, Long> {

    Optional<Book> getBookByName(String name);
    List<Book> findAll();
    List<Book> findAllByAuthor(Author author);
    List<Book> findAllByGenre(Genre genre);
    List<Book> findAllByAuthorAndGenre(Author author, Genre genre);

}