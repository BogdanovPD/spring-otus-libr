package ru.otus.spring.libr;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import ru.otus.spring.libr.dto.BookDto;
import ru.otus.spring.libr.dto.BookRequest;

@MessagingGateway
public interface Libr {

    @Gateway(requestChannel = "newBooksChannel")
    void newBook(BookDto bookDto);
    @Gateway(requestChannel = "bookRequestsChannel")
    void bookRequest(BookRequest bookRequest);

}
