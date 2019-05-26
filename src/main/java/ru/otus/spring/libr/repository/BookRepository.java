package ru.otus.spring.libr.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.libr.entities.Book;

import java.util.List;

public interface BookRepository extends MongoRepository<Book, String> {

    List<Book> findAllByNameAndAuthor(String name, String author);
    List<Book> findAllByNameAndGenre(String name, String genre);
    List<Book> findAllByAuthorAndGenre(String author, String genre);
    List<Book> findAllByNameAndAuthorAndGenre(String name, String author, String genre);
    List<Book> findAllByName(String name);
    List<Book> findAllByAuthor(String author);
    List<Book> findAllByGenre(String genre);
}

