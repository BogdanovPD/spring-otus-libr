package ru.otus.spring.libr;

import org.junit.After;
import org.junit.Assert;
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
import static org.junit.Assert.assertNotNull;

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

    @After
    public void afterEach() {
        bookRepository.deleteAll();
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
        StepVerifier.create(newBook).consumeNextWith(b -> {
            Flux<Book> books = librService.getAllBooks();
            StepVerifier.create(books)
                    .expectNext(b)
                    .verifyComplete();// тут почему-то уходит в бесконечный цикл :(
        })
                .verifyComplete();
    }

//    @Test
//    public void getAllBooksByNameTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByName(BOOK).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByAuthorTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByAuthor(AUTHOR).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByGenreTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByGenre(GENRE).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByNameAndAuthorTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByNameAndAuthor(BOOK, AUTHOR).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByNameAndGenreTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByNameAndGenre(BOOK, GENRE).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByAuthorAndGenreTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByAuthorAndGenre(AUTHOR, GENRE).collectList().subscribe(books -> {
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void getAllBooksByNameAndAuthorAndGenreTest() {
//        Mono<Book> newBook = librService.newBook(BOOK, GENRE, AUTHOR);
//        newBook.subscribe(book -> librService.getBooksByNameAndAuthorAndGenre(BOOK, AUTHOR, GENRE)
//                .collectList().subscribe(books -> {
//                    System.out.println(books.size());
//            assertEquals(books.size(), 1);
//            assertEquals(books.get(0), book);
//        }));
//    }
//
//    @Test
//    public void saveBookTest(){
//        librService.newBook(BOOK, GENRE, AUTHOR).subscribe(b -> {
//            String author1 = AUTHOR.concat("1");
//            b.setAuthor(author1);
//            StepVerifier.create(librService.saveBook(b))
//                    .assertNext(b1 -> assertEquals(b1.getAuthor(), author1))
//                    .expectComplete()
//                    .verify();
//
//        });
//    }

//    @Test
//    public void addCommentTest(){
//        Book book = librService.newBook(BOOK, GENRE, AUTHOR).get();
//        String comment = "Comment";
//        book.getComments().add(comment);
//        librService.saveBook(book);
//        Book book1 = librService.getAllBooks().get(0);
//        String comment1 = book1.getComments().get(0);
//        assertEquals(comment, comment1);
//    }

}
