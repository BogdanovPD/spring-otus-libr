package ru.otus.spring.libr.books.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.Libr;
import ru.otus.spring.libr.books.entities.Author;
import ru.otus.spring.libr.books.entities.Book;
import ru.otus.spring.libr.books.entities.Genre;
import ru.otus.spring.libr.books.services.BookEmissionService;
import ru.otus.spring.libr.books.services.BooksService;
import ru.otus.spring.libr.dto.BookDto;
import ru.otus.spring.libr.util.RandomNameUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookEmissionServiceImpl implements BookEmissionService {

    private final BooksService booksService;
    private final Libr libr;

    @Override
    @Scheduled(fixedDelay = 15000)
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
        book = booksService.saveBook(book);
        log.info("New book: " + book);
        log.info("===================================================================================================");
        libr.newBook(BookDto.builder()
                .id(book.getId())
                .author(book.getAuthor().getName())
                .bookName(book.getName())
                .build()
        );
    }
}
