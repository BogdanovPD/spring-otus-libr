package ru.otus.spring.libr.books.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.books.entities.Author;
import ru.otus.spring.libr.books.entities.Book;
import ru.otus.spring.libr.books.entities.Genre;
import ru.otus.spring.libr.books.services.BookEmissionService;
import ru.otus.spring.libr.books.services.BooksService;
import ru.otus.spring.libr.randoms.RandomNameUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookEmissionServiceImpl implements BookEmissionService {

    private final BooksService booksService;

    @Override
    @Scheduled(fixedDelay = 5000)
    public void emitBook() {
        Author author = Author.builder()
                .name(RandomNameUtil.getRandomAuthor())
                .build();
        Genre genre = Genre.builder()
                .name(RandomNameUtil.getRandomGenre())
                .build();
        Book book = Book.builder()
                .author(author)
                .genre(genre)
                .name(RandomNameUtil.getRandomBook())
                .build();
        booksService.saveBook(book);
        log.info("New book: " + book);
        log.info("===================================================================================================");
    }
}
