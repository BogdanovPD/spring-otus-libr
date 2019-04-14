package ru.otus.spring.libr.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.services.LibrDaoService;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ShellComponent
@RequiredArgsConstructor
@Slf4j
public class ShellCommands {

    private final LibrDaoService librDaoService;
    private final String all = "all";

    @ShellMethod("Create new book")
    public String newBook(
            @ShellOption(value = "-a", help = "Book's Author Name") @Size(max = 255) String author,
            @ShellOption(value = "-n", help = "Book's Name") @Size(max = 255) String name,
            @ShellOption(value = "-g", help = "Book's Genre Name") @Size(max = 255) String genre) {
        Optional<Author> authorOptional = librDaoService.getAuthorByName(author);
        if (authorOptional.isEmpty()) {
            librDaoService.saveAuthor(Author.builder()
                    .name(author)
                    .build());
            authorOptional = librDaoService.getAuthorByName(author);
            if (authorOptional.isEmpty()) {
                log.error("Error! Author cannot be received or saved!");
                return "";
            }
        }
        Optional<Genre> genreOptional = librDaoService.getGenreByName(genre);
        if (genreOptional.isEmpty()) {
            librDaoService.saveGenre(Genre.builder()
                    .name(genre)
                    .build());
            genreOptional = librDaoService.getGenreByName(name);
            if (genreOptional.isEmpty()) {
                log.error("Error! Genre cannot be received or saved!");
                return "";
            }
        }
        Optional<Book> bookOptional = librDaoService.getBookByName(name);
        if (bookOptional.isPresent()) {
            log.error("Error! Book is already present. Input a different name");
            return "";
        }
        librDaoService.saveBook(Book.builder()
                .author(authorOptional.get())
                .genre(genreOptional.get())
                .name(name)
                .build());
        bookOptional = librDaoService.getBookByName(name);
        if (bookOptional.isEmpty()) {
            log.error("Error! Book hasn't been saved!");
            return "";
        }
        return bookOptional.get().toString();
    }

    @ShellMethod("Select books")
    public List<Book> selectBooks(
            @ShellOption(value = "-a", help = "Author to filter by", defaultValue = "all")
            @Size(max = 255) String author,
            @ShellOption(value = "-n", help = "Name to filter by (ignores other filters)", defaultValue = "all")
            @Size(max = 255) String name,
            @ShellOption(value = "-g", help = "Genre to filter by", defaultValue = "all")
            @Size(max = 255) String genre
    ) {
        if (!all.equals(name)) {
            Optional<Book> bookOptional = librDaoService.getBookByName(name);
            if (bookOptional.isEmpty()) {
                return Collections.emptyList();
            }
            return Arrays.asList(bookOptional.get());
        }
        if (!all.equals(author) && !all.equals(genre)) {
            return librDaoService.getBooksByAuthorAndGenre(author, genre);
        }
        if (!all.equals(author)) {
            return librDaoService.getBooksByAuthor(author);
        }
        if (!all.equals(genre)) {
            return librDaoService.getBooksByGenre(genre);
        }
        return librDaoService.getAllBooks();
    }

    @ShellMethod("Select authors")
    public List<Author> selectAuthors(
            @ShellOption(value = "-n", help = "Author to filter by", defaultValue = "all")
            @Size(max = 255) String author
    ) {
        if (!all.equals(author)) {
            Optional<Author> authorOptional = librDaoService.getAuthorByName(author);
            if (authorOptional.isEmpty()) {
                return Collections.emptyList();
            }
            return Arrays.asList(authorOptional.get());
        }
        return librDaoService.getAllAuthors();
    }

    @ShellMethod("Select genres")
    public List<Genre> selectGenres(
            @ShellOption(value = "-n", help = "Genre to filter by", defaultValue = "all")
            @Size(max = 255) String genre
    ) {
        if (!all.equals(genre)) {
            Optional<Genre> genreOptional = librDaoService.getGenreByName(genre);
            if (genreOptional.isEmpty()) {
                return Collections.emptyList();
            }
            return Arrays.asList(genreOptional.get());
        }
        return librDaoService.getAllGenres();
    }

    @ShellMethod("Add new comment to book")
    public String newComment(
            @ShellOption(value = "-b", help = "Book's name") String bookName,
            @ShellOption(value = "-c", help = "Comment") String comment
    ) {
        if (comment.isBlank()) {
            log.error("Error! Comment is blank");
            return "";
        }
        Optional<Book> bookOptional = librDaoService.getBookByName(bookName);
        if (bookOptional.isEmpty()) {
            log.error("Error! Book is not found");
            return "";
        }
        Book book = bookOptional.get();
        Comment commentEntity = Comment.builder()
                .book(book)
                .text(comment)
                .build();
        librDaoService.addComment(book, commentEntity);
        return commentEntity.toString();
    }

    @ShellMethod("Select all comments by book")
    public List<String> selectComments(
            @ShellOption(value = "-b", help = "Book's name") String bookName
    ) {
        Optional<Book> bookOptional = librDaoService.getBookByName(bookName);
        if (bookOptional.isEmpty()) {
            log.error("Error! Book is not found");
            return Collections.emptyList();
        }
        Book book = bookOptional.get();
        return librDaoService.getCommentsByBook(book).stream()
                .map(Comment::getText).collect(Collectors.toList());
    }

}