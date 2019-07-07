package ru.otus.spring.libr.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.dto.BookDto;
import ru.otus.spring.libr.library.entity.BookClient;
import ru.otus.spring.libr.library.services.BookClientService;

@Slf4j
@Service
@RequiredArgsConstructor
public class NewBookProcessingService {

    private final BookClientService bookClientService;

    public void save(BookDto bookDto) {
        log.info("===================================================================================================");
        log.info("Saving of a new book " + bookDto);
        bookClientService.save(BookClient.builder()
                .bookId(bookDto.getId())
                .bookAuthor(bookDto.getAuthor())
                .bookName(bookDto.getBookName())
                .build()
        );
        log.info("Book successfully saved");
        log.info("===================================================================================================");
    }

}
