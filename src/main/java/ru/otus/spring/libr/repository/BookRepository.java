package ru.otus.spring.libr.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.spring.libr.entities.Book;

@Repository
public interface BookRepository extends ReactiveMongoRepository<Book, String> {
    Flux<Book> findAllByNameAndAuthor(String name, String author);
    Flux<Book> findAllByNameAndGenre(String name, String genre);
    Flux<Book> findAllByAuthorAndGenre(String author, String genre);
    Flux<Book> findAllByNameAndAuthorAndGenre(String name, String author, String genre);
    Flux<Book> findAllByName(String name);
    Flux<Book> findAllByAuthor(String author);
    Flux<Book> findAllByGenre(String genre);
}

