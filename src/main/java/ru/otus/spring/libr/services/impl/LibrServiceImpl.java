package ru.otus.spring.libr.services.impl;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class LibrServiceImpl implements LibrService {

    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;

    @HystrixCommand(fallbackMethod = "voidAuthorFallback")
    @Override
    public void saveAuthor(Author author) {
        authorRepository.save(author);
    }

    @HystrixCommand(fallbackMethod = "singleAuthorFallback")
    @Override
    public Optional<Author> getAuthorByName(String name) {
        return authorRepository.getAuthorByName(name);
    }

    @HystrixCommand(fallbackMethod = "listAuthorFallback")
    @Override
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "voidGenreFallback")
    @Override
    public void saveGenre(Genre genre) {
        genreRepository.save(genre);
    }

    @HystrixCommand(fallbackMethod = "singleGenreFallback")
    @Override
    public Optional<Genre> getGenreByName(String name) {
        return genreRepository.getGenreByName(name);
    }

    @HystrixCommand(fallbackMethod = "listGenreFallback")
    @Override
    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "singleBookFallbackName")
    @Override
    public Optional<Book> getBookByName(String name) {
        return bookRepository.getBookByName(name);
    }

    @HystrixCommand(fallbackMethod = "listBookFallback")
    @Override
    @PostFilter("hasRole('ROLE_ADMIN') or hasPermission(filterObject, 'READ')")
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @HystrixCommand(fallbackMethod = "listBookFallbackAuthor")
    @Override
    public List<Book> getBooksByAuthor(String author) {
        Optional<Author> authorOptional = authorRepository.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findAllByAuthor(authorOptional.get());
    }

    @HystrixCommand(fallbackMethod = "listBookFallbackGenre")
    @Override
    public List<Book> getBooksByGenre(String genre) {
        Optional<Genre> genreOptional = genreRepository.getGenreByName(genre);
        if (genre.isEmpty()) {
            return Collections.emptyList();
        }
        return bookRepository.findAllByGenre(genreOptional.get());
    }

    @HystrixCommand(fallbackMethod = "listBookFallbackAuthorAndGenre")
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

    @HystrixCommand(fallbackMethod = "singleBookFallback")
    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Book saveBook(Book book) {
        Optional<Author> authorOpt = authorRepository.getAuthorByName(book.getAuthor().getName());
        authorOpt.ifPresentOrElse(a -> book.setAuthor(a),
                () -> book.setAuthor(authorRepository.save(book.getAuthor())));
        Optional<Genre> genreOpt = genreRepository.getGenreByName(book.getGenre().getName());
        genreOpt.ifPresentOrElse(g -> book.setGenre(g),
                () -> book.setGenre(genreRepository.save(book.getGenre())));
        return bookRepository.save(book);
    }

    @Override
    @HystrixCommand(fallbackMethod = "voidCommentFallback")
    public void addComment(Book book, Comment comment) {
        comment.setBook(book);
        commentRepository.save(comment);
    }

    @HystrixCommand(fallbackMethod = "listCommentFallback")
    @Override
    public List<Comment> getCommentsByBook(Book book) {
        return commentRepository.findAllByBook(book);
    }

    @HystrixCommand(fallbackMethod = "listBookFallbackNameAndAuthor")
    @Override
    public List<Book> getBooksByNameAndAuthor(String name, String author) {
        return bookRepository.findAllByNameAndAuthor_Name(name, author);
    }

    public List<Author> listAuthorFallback() {
        log.error("Problems occurred during receiving authors from DB");
        return Collections.emptyList();
    }

    public List<Genre> listGenreFallback() {
        log.error("Problems occurred during receiving genres from DB");
        return Collections.emptyList();
    }

    public List<Book> listBookFallback() {
        log.error("Problems occurred during receiving books from DB");
        return Collections.emptyList();
    }

    public List<Book> listBookFallbackAuthor(String author) {
        log.error("Problems occurred during receiving books from DB by author: " + author);
        return Collections.emptyList();
    }

    public List<Book> listBookFallbackGenre(String genre) {
        log.error("Problems occurred during receiving books from DB by genre: " + genre);
        return Collections.emptyList();
    }

    public List<Book> listBookFallbackAuthorAndGenre(String author, String genre) {
        log.error("Problems occurred during receiving books from DB by author: " + author + " and genre: " + genre);
        return Collections.emptyList();
    }

    public List<Book> listBookFallbackNameAndAuthor(String name, String author) {
        log.error("Problems occurred during receiving books from DB by author: " + author + " and book name: " + name);
        return Collections.emptyList();
    }


    public List<Comment> listCommentFallback(Book book) {
        log.error("Problems occurred during receiving comments from DB by book: " + book);
        return Collections.emptyList();
    }

    public Optional<Author> singleAuthorFallback(String name) {
        log.error("Problems occurred during receiving author from DB: " + name);
        return Optional.of(Author.builder().name(name).build());
    }

    public Optional<Genre> singleGenreFallback(String name) {
        log.error("Problems occurred during receiving genre from DB: " + name);
        return Optional.of(Genre.builder().name(name).build());
    }

    public Optional<Book> singleBookFallbackName(String name) {
        log.error("Problems occurred during receiving book from DB: " + name);
        return Optional.of(Book.builder()
                .name(name)
                .author(Author.builder().name("").build())
                .genre(Genre.builder().name("").build())
                .comments(Collections.emptyList())
                .build());
    }

    public Book singleBookFallback(Book book) {
        log.error("Problems occurred during saving book to DB: " + book);
        return book;
    }

    public void voidAuthorFallback(Author author) {
        log.error("Problems occurred during saving book author " + author);
    }

    public void voidGenreFallback(Genre genre) {
        log.error("Problems occurred during saving book genre " + genre);
    }

    public void voidCommentFallback(Book book, Comment comment) {
        log.error("Problems occurred during saving comment " + comment +" of book " + book);
    }
}