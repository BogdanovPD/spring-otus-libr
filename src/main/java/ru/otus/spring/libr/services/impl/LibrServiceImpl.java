package ru.otus.spring.libr.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.repository.AuthorRepository;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.repository.CommentRepository;
import ru.otus.spring.libr.repository.GenreRepository;
import ru.otus.spring.libr.services.LibrService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibrServiceImpl implements LibrService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        return authorRepository.getAuthorByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.getGenreByName(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        return bookRepository.getBookByName(name);
    }

    @Override
    @PostFilter("hasRole('ROLE_ADMIN') or hasPermission(filterObject, 'READ')")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        Optional<Author> authorOptional = authorRepository.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findAllByAuthor(authorOptional.get());
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        Optional<Genre> genreOptional = genreRepository.getGenreByName(genre);
        if (genre.isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findAllByGenre(genreOptional.get());
    }

    @Override
    public List<Book> getBooksByAuthorAndGenre(String author, String genre) {
        Optional<Author> authorOptional = authorRepository.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Optional<Genre> genreOptional = genreRepository.getGenreByName(genre);
        if (genre.isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findAllByAuthorAndGenre(authorOptional.get(), genreOptional.get());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Book saveBook(Book book) {
        Optional<Author> authorOpt = authorRepository.getAuthorByName(book.getAuthor().getName());
        authorOpt.ifPresent(a -> book.setAuthor(a));
        Optional<Genre> genreOpt = genreRepository.getGenreByName(book.getGenre().getName());
        genreOpt.ifPresent(g -> book.setGenre(g));
        return bookRepository.save(book);
    }

    @Override
    public void addComment(Book book, Comment comment) {
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByBook(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @Override
    public List<Book> getBooksByNameAndAuthor(String name, String author) {
        return bookRepository.findAllByNameAndAuthor_Name(name, author);
    }
}