package ru.otus.spring.libr.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.dao.AuthorsDao;
import ru.otus.spring.libr.dao.BooksDao;
import ru.otus.spring.libr.dao.GenresDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.services.LibrDaoService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class LibrDaoServiceImpl implements LibrDaoService {

    private final AuthorsDao authorsDao;
    private final GenresDao genresDao;
    private final BooksDao booksDao;

    @Override
    public void newAuthor(String author) {
        Optional<Author> authorByName = getAuthorByName(author);
        if (authorByName.isEmpty()) {
            authorsDao.saveAuthor(Author.builder()
                    .name(author)
                    .build());
        }
    }

    @Override
    public void saveAuthor(Author author) {
        authorsDao.saveAuthor(author);
    }

    @Override
    public Optional<Author> getAuthorByName(String name) {
        return authorsDao.getAuthorByName(name);
    }

    @Override
    public List<Author> getAllAuthors() {
        return authorsDao.getAllAuthors();
    }

    @Override
    public void newGenre(String genre) {
        Optional<Genre> genreByName = getGenreByName(genre);
        if (genreByName.isEmpty()) {
            genresDao.saveGenre(Genre.builder()
                    .name(genre)
                    .build());
        }
    }

    @Override
    public void saveGenre(Genre genre) {
        genresDao.saveGenre(genre);
    }

    @Override
    public Optional<Genre> getGenreByName(String name) {
        return genresDao.getGenreByName(name);
    }

    @Override
    public List<Genre> getAllGenres() {
        return genresDao.getAllGenres();
    }

    @Override
    public Optional<Book> getBookByName(String name) {
        return booksDao.getBookByName(name);
    }

    @Override
    public List<Book> getAllBooks() {
        return booksDao.getAllBooks();
    }

    @Override
    public List<Book> getBooksByAuthor(String author) {
        Optional<Author> authorOptional = authorsDao.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        return booksDao.getAllBooksByAuthor(authorOptional.get());
    }

    @Override
    public List<Book> getBooksByGenre(String genre) {
        Optional<Genre> genreOptional = genresDao.getGenreByName(genre);
        if (genre.isEmpty()) {
            return Collections.emptyList();
        }
        return booksDao.getAllBooksByGenre(genreOptional.get());
    }

    @Override
    public List<Book> getBooksByAuthorAndGenre(String author, String genre) {
        Optional<Author> authorOptional = authorsDao.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            return Collections.emptyList();
        }
        Optional<Genre> genreOptional = genresDao.getGenreByName(genre);
        if (genre.isEmpty()) {
            return Collections.emptyList();
        }
        return booksDao.getAllBooksByAuthorAndGenre(authorOptional.get(), genreOptional.get());
    }

    @Override
    public void newBook(String name, Author author, Genre genre) {
        booksDao.saveBook(Book.builder()
                .name(name)
                .author(author)
                .genre(genre)
                .build());
    }

    @Override
    public void saveBook(Book book) {
        booksDao.saveBook(book);
    }

    @Override
    public void addComment(Book book, Comment comment) {
        comment.setBook(book);
        booksDao.loadComments(book).getComments().add(comment);
    }

    @Override
    public List<Comment> getCommentsByBook(Book book) {
        return booksDao.loadComments(book).getComments();
    }
}
