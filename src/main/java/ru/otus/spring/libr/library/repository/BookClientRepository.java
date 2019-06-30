package ru.otus.spring.libr.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.library.entity.BookClient;

import java.util.List;

public interface BookClientRepository extends CrudRepository<BookClient, Long> {

    List<BookClient> getAllByBookAuthorAndBookNameAndClientIdEquals(String author, String bookName, long clientId);
    List<BookClient> findAllByClientIdIsNot(long clientId);

}
