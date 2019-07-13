package ru.otus.spring.libr;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.repository.AuthorRepository;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.repository.CommentRepository;
import ru.otus.spring.libr.repository.GenreRepository;
import ru.otus.spring.libr.services.LibrService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor
public class LibrServiceTests {

    @Autowired
    private LibrService librService;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CommentRepository commentRepository;

    @AfterEach
    public void afterEach() {
        commentRepository.deleteAll();
        bookRepository.deleteAll();
        authorRepository.deleteAll();
        genreRepository.deleteAll();
    }

    @Test
    public void getAllAuthorsTest_empty() {
        List<Author> authors = librService.getAllAuthors();
        assertThat(authors, empty());
    }

    @Test
    public void getAllGenresTest_empty() {
        List<Genre> genres = librService.getAllGenres();
        assertThat(genres, empty());
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getAllBooksTest_empty() {
        List<Book> books = librService.getAllBooks();
        assertThat(books, empty());
    }

    @Test
    public void getAuthorByNameTest() {
        String testName = "Test Name";
        Author author = createAuthor(testName);
        Author authorReceived = librService.getAuthorByName(testName).get();
        assertThat(author, is(authorReceived));
    }

    @Test
    public void getAllAuthorsTest() {
        String testName = "Test Name";
        Author author = createAuthor(testName);
        String testName1 = "Test Name 1";
        Author author1 = createAuthor(testName1);
        List<Author> authors = librService.getAllAuthors();
        assertThat(authors.size(), is(2));
        assertThat(authors, containsInAnyOrder(author, author1));
    }

    @Test
    public void getGenreByNameTest() {
        String testName = "Test Name";
        Genre genre = createGenre(testName);
        Genre genreReceived = librService.getGenreByName(testName).get();
        assertThat(genre, is(genreReceived));
    }

    @Test
    public void getAllGenresTest() {
        String testName = "Test Name";
        Genre genre = createGenre(testName);
        String testName1 = "Test Name 1";
        Genre genre1 = createGenre(testName1);
        List<Genre> genres = librService.getAllGenres();
        assertThat(genres.size(), is(2));
        assertThat(genres, containsInAnyOrder(genre, genre1));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getBookByNameTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        Book book = createBook(author, genre, bookName);
        Book bookReceived = librService.getBookByName(bookName).get();
        assertThat(book, is(bookReceived));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getBooksByAuthorTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librService.getBooksByAuthor(authorName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getAuthor(), is(author));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getBooksByGenreTest_admin() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librService.getBooksByGenre(genreName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getGenre(), is(genre));
    }

    @Test
    @WithMockUser(roles = {"USER"})
    public void createBook_user() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        assertThrows(AccessDeniedException.class,
                () -> createBook(author, genre, bookName));
    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void getBooksByAuthorAndGenreTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librService.getBooksByAuthorAndGenre(authorName, genreName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getGenre(), is(genre));
        assertThat(books.get(0).getAuthor(), is(author));
    }

//    @Test
//    @WithMockUser(roles = {"ADMIN"})
//    public void getAllBooksTest() {
//        String genreName = "Author Name";
//        Genre genre = createGenre(genreName);
//        String authorName = "Genre Name";
//        Author author = createAuthor(authorName);
//        String bookName = "Book Name";
//        Book book = createBook(author, genre, bookName);
//        String genreName1 = "Author Name1";
//        Genre genre1 = createGenre(genreName1);
//        String authorName1 = "Genre Name1";
//        Author author1 = createAuthor(authorName1);
//        String bookName1 = "Book Name1";
//        Book book1 = createBook(author1, genre1, bookName1);
//        List<Book> books = librService.getAllBooks();
//        assertThat(books.size(), is(2));
//        assertThat(books, containsInAnyOrder(book, book1));
//    }

    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void addCommentTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        Book book = createBook(author, genre, bookName);
        Comment goodBook = Comment.builder()
                .book(book)
                .text("Good book")
                .build();
        librService.addComment(book, goodBook);
        assertThat(librService.getCommentsByBook(book).get(0), is(goodBook));
    }

    private Author createAuthor(String name) {
        librService.saveAuthor(Author.builder()
                .name(name)
                .build());
        return librService.getAuthorByName(name).get();
    }

    private Genre createGenre(String name) {
        librService.saveGenre(Genre.builder()
                .name(name)
                .build());
        return librService.getGenreByName(name).get();
    }

    private Book createBook(Author author, Genre genre, String name) {
        librService.saveBook(Book.builder()
                .author(author)
                .genre(genre)
                .name(name)
                .build());
        return librService.getBookByName(name).get();
    }

}