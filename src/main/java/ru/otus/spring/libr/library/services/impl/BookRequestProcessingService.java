package ru.otus.spring.libr.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.dto.BookRequest;
import ru.otus.spring.libr.library.entity.BookClient;
import ru.otus.spring.libr.library.entity.DelayedRequest;
import ru.otus.spring.libr.library.services.BookClientService;
import ru.otus.spring.libr.library.services.DelayedRequestService;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookRequestProcessingService {

    private final BookClientService bookClientService;
    private final DelayedRequestService delayedRequestService;

    public void processRequest(BookRequest bookRequest) {
        log.info("===================================================================================================");
        log.info("Book request: client [id=" + bookRequest.getClientId() + ", name=" + bookRequest.getClientName()
                + "asks for book [author=" + bookRequest.getAuthor() + ", name=" + bookRequest.getBookName() + "]");
        Optional<BookClient> bookClientOpt =
                bookClientService.getByAuthorAndBookName(bookRequest.getAuthor(), bookRequest.getBookName());
        if (bookClientOpt.isEmpty()) {
            log.info("Book doesn't found");
            log.info("===================================================================================================");
            delayedRequestService.saveRequest(bookRequest);
            return;
        }
        bookClientService.setClientToBook(bookClientOpt.get(), bookRequest.getClientId(), bookRequest.getClientName());
        log.info("Book has been found. User has taken a book");
        log.info("===================================================================================================");
    }

}
