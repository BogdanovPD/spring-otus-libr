package ru.otus.spring.libr;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.services.LibrService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ExtendWith(SpringExtension.class)
@ActiveProfiles("test")
public class LibrServiceTest {

    private static final String AUTHOR = "Author";
    private static final String GENRE = "Genre";
    private static final String BOOK = "Book";

    @Autowired
    private LibrService librService;
    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    public void afterEach() {
        bookRepository.deleteAll();
    }

    @Test
    public void newBookTest() {
        Book book = Book.builder()
                .author(AUTHOR)
                .genre(GENRE)
                .name(BOOK)
                .build();
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        assertTrue(newBook.isPresent());
        assertEquals(book, newBook.get());
    }

    @Test
    public void getAllBooksTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getAllBooks();
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByNameTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByName(BOOK);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByAuthorTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByAuthor(AUTHOR);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByGenreTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByGenre(GENRE);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByNameAndAuthorTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByNameAndAuthor(BOOK, AUTHOR);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByNameAndGenreTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByNameAndGenre(BOOK, GENRE);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByAuthorAndGenreTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByAuthorAndGenre(AUTHOR, GENRE);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void getAllBooksByNameAndAuthorAndGenreTest() {
        Optional<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        List<Book> books = librService.getBooksByNameAndAuthorAndGenre(BOOK, AUTHOR, GENRE);
        assertEquals(books.size(), 1);
        assertEquals(books.get(0), newBook.get());
    }

    @Test
    public void saveBookTest(){
        Book book = librService.newBook(BOOK, GENRE, AUTHOR).get();
        String author1 = AUTHOR.concat("1");
        book.setAuthor(author1);
        librService.saveBook(book);
        Book book1 = librService.getAllBooks().get(0);
        assertEquals(book1.getAuthor(), author1);
    }

    @Test
    public void addCommentTest(){
        Book book = librService.newBook(BOOK, GENRE, AUTHOR).get();
        String comment = "Comment";
        book.getComments().add(comment);
        librService.saveBook(book);
        Book book1 = librService.getAllBooks().get(0);
        String comment1 = book1.getComments().get(0);
        assertEquals(comment, comment1);
    }

}
