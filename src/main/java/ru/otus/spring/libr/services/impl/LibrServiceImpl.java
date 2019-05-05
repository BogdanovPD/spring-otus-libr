package ru.otus.spring.libr.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.services.LibrService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class LibrServiceImpl implements LibrService {

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> newBook(String name, String genre, String author) {
        List<Book> existedBooks = bookRepository.findAllByNameAndAuthor(name, author);
        if (!existedBooks.isEmpty()) {
            log.error("Described book already exists");
            return Optional.empty();
        }
        return Optional.ofNullable(bookRepository.save(Book.builder()
                .name(name)
                .author(author)
                .genre(genre)
                .comments(new ArrayList<>())
                .build()));
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByName(String name) {
        List<Book> books = bookRepository.findAllByName(name);
        if (books.isEmpty()) {
            log.warn("Cannot find any books by name \"" + name + "\"");
        }
        return books;
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findAllByAuthor(author);
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        return bookRepository.findAllByGenre(genre);
    }

    @Override
    public List<Book> getBooksByNameAndAuthor(String name, String author) {
        return bookRepository.findAllByNameAndAuthor(name, author);
    }

    @Override
    public List<Book> getBooksByNameAndGenre(String name, String genre) {
        return bookRepository.findAllByNameAndGenre(name, genre);
    }

    @Override
    public List<Book> getBooksByAuthorAndGenre(String author, String genre) {
        return bookRepository.findAllByAuthorAndGenre(author, genre);
    }

    @Override
    public List<Book> getBooksByNameAndAuthorAndGenre(String name, String author, String genre) {
        return bookRepository.findAllByNameAndAuthorAndGenre(name, author, genre);
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}


