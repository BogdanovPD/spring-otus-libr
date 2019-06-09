package ru.otus.spring.libr.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;

import java.util.List;

public interface CommentRepository extends CrudRepository<Comment, Long> {

    List<Comment> findAllByBook(Book book);

}