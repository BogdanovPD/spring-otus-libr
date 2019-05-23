package ru.otus.spring.libr;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.otus.spring.libr.entities.Book;
import ru.otus.spring.libr.repository.BookRepository;
import ru.otus.spring.libr.services.LibrService;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class LibrServiceTest {

    private static final String AUTHOR = "Author";
    private static final String GENRE = "Genre";
    private static final String BOOK = "Book";

    @Autowired
    private LibrService librService;
    @Autowired
    private BookRepository bookRepository;

    @Before
    public void before() {
        bookRepository.deleteAll().block();
    }

    @Test
    public void newBookTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        StepVerifier.create(newBook)
                .assertNext(Assert::assertNotNull)
                .verifyComplete();
    }

    @Test
    public void getAllBooksTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getAllBooks();
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByNameTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByName(BOOK);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByAuthorTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByAuthor(AUTHOR);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByGenreTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByGenre(GENRE);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByNameAndGenreTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByNameAndGenre(BOOK, GENRE);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByAuthorAndGenreTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByAuthorAndGenre(AUTHOR, GENRE);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void getAllBooksByNameAndAuthorAndGenreTest() {
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        Flux<Book> books = librService.getBooksByNameAndAuthorAndGenre(BOOK, AUTHOR, GENRE);
        StepVerifier.create(books)
                .expectNext(newBook.block())
                .verifyComplete();
    }

    @Test
    public void saveBookTest(){
        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
        String author1 = AUTHOR.concat("1");
        Book book = newBook.block();
        book.setAuthor(author1);
        Mono<Book> bookMono = librService.saveBook(book);
        StepVerifier.create(bookMono)
                .assertNext(b -> assertEquals(b.getAuthor(), author1))
                .verifyComplete();
    }

    @Test
    public void addCommentTest(){
        Book book = librService.newBook(BOOK, GENRE, AUTHOR).block();
        String comment = "Comment";
        book.getComments().add(comment);
        Mono<Book> bookMono = librService.saveBook(book);
        StepVerifier.create(bookMono)
                .assertNext(b -> assertEquals(b.getComments().get(0), comment))
                .verifyComplete();

    }

}
