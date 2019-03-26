package ru.otus.spring.libr;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.otus.spring.libr.dao.AuthorsDao;
import ru.otus.spring.libr.dao.BooksDao;
import ru.otus.spring.libr.dao.GenresDao;
import ru.otus.spring.libr.entities.Author;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.entities.Comment;
import ru.otus.spring.libr.entities.Genre;
import ru.otus.spring.libr.services.LibrDaoService;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"
})
@ActiveProfiles("test")
public class LibrApplicationTests {

    @Autowired
    private LibrDaoService librDaoService;
    @Autowired
    private AuthorsDao authorsDao;
    @Autowired
    private BooksDao booksDao;
    @Autowired
    private GenresDao genresDao;

    @After
    public void after() {
        booksDao.deleteAllComments();
        booksDao.deleteAllBooks();
        authorsDao.deleteAllAuthors();
        genresDao.deleteAllGenres();
    }

    @Test
    public void getAllAuthorsTest_empty() {
        List<Author> authors = librDaoService.getAllAuthors();
        assertThat(authors, empty());
    }

    @Test
    public void getAllGenresTest_empty() {
        List<Genre> genres = librDaoService.getAllGenres();
        assertThat(genres, empty());
    }

    @Test
    public void getAllBooksTest_empty() {
        List<Book> books = librDaoService.getAllBooks();
        assertThat(books, empty());
    }

    @Test
    public void getAuthorByNameTest() {
        String testName = "Test Name";
        Author author = createAuthor(testName);
        Author authorReceived = librDaoService.getAuthorByName(testName).get();
        assertThat(author, is(authorReceived));
    }

    @Test
    public void getAllAuthorsTest() {
        String testName = "Test Name";
        Author author = createAuthor(testName);
        String testName1 = "Test Name 1";
        Author author1 = createAuthor(testName1);
        List<Author> authors = librDaoService.getAllAuthors();
        assertThat(authors.size(), is(2));
        assertThat(authors, containsInAnyOrder(author, author1));
    }

    @Test
    public void getGenreByNameTest() {
        String testName = "Test Name";
        Genre genre = createGenre(testName);
        Genre genreReceived = librDaoService.getGenreByName(testName).get();
        assertThat(genre, is(genreReceived));
    }

    @Test
    public void getAllGenresTest() {
        String testName = "Test Name";
        Genre genre = createGenre(testName);
        String testName1 = "Test Name 1";
        Genre genre1 = createGenre(testName1);
        List<Genre> genres = librDaoService.getAllGenres();
        assertThat(genres.size(), is(2));
        assertThat(genres, containsInAnyOrder(genre, genre1));
    }

    @Test
    public void getBookByNameTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        Book book = createBook(author, genre, bookName);
        Book bookReceived = librDaoService.getBookByName(bookName).get();
        assertThat(book, is(bookReceived));
    }

    @Test
    public void getBooksByAuthorTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librDaoService.getBooksByAuthor(authorName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getAuthor(), is(author));
    }

    @Test
    public void getBooksByGenreTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librDaoService.getBooksByGenre(genreName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getGenre(), is(genre));
    }

    @Test
    public void getBooksByAuthorAndGenreTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        createBook(author, genre, bookName);
        List<Book> books = librDaoService.getBooksByAuthorAndGenre(authorName, genreName);
        assertThat(books.size(), is(1));
        assertThat(books.get(0).getGenre(), is(genre));
        assertThat(books.get(0).getAuthor(), is(author));
    }

    @Test
    public void getAllBooksTest() {
        String genreName = "Author Name";
        Genre genre = createGenre(genreName);
        String authorName = "Genre Name";
        Author author = createAuthor(authorName);
        String bookName = "Book Name";
        Book book = createBook(author, genre, bookName);
        String genreName1 = "Author Name1";
        Genre genre1 = createGenre(genreName1);
        String authorName1 = "Genre Name1";
        Author author1 = createAuthor(authorName1);
        String bookName1 = "Book Name1";
        Book book1 = createBook(author1, genre1, bookName1);
        List<Book> books = librDaoService.getAllBooks();
        assertThat(books.size(), is(2));
        assertThat(books, containsInAnyOrder(book, book1));
    }

    @Test
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
        librDaoService.addComment(book, goodBook);
        assertThat(librDaoService.getCommentsByBook(book).get(0), is(goodBook));
    }

    private Author createAuthor(String name) {
        librDaoService.newAuthor(name);
        return librDaoService.getAuthorByName(name).get();
    }

    private Genre createGenre(String name) {
        librDaoService.newGenre(name);
        return librDaoService.getGenreByName(name).get();
    }

    private Book createBook(Author author, Genre genre, String name) {
        librDaoService.newBook(name, author, genre);
        return librDaoService.getBookByName(name).get();
    }

}
