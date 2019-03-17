package ru.otus.spring.libr.shell;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.services.LibrDaoService;

import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional<Author> authorOptional = librDaoService.saveAuthor(author);
        if (authorOptional.isEmpty()) {
            return "Error! Author cannot be received or saved!";
        }
        Optional<Genre> genreOptional = librDaoService.saveGenre(genre);
        if (genreOptional.isEmpty()) {
            return "Error! Genre cannot be received or saved!";
        }
        Optional<Book> bookOptional = librDaoService.getBookByName(name);
        if (bookOptional.isPresent()) {
            return "Error! Book is already present. Input a different name";
        }
        Optional<Book> optionalBook = librDaoService.saveBook(name, authorOptional.get(), genreOptional.get());
        if (optionalBook.isEmpty()) {
            return "Error! Book hasn't been saved!";
        }
        return optionalBook.get().toString();
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

}