package ru.otus.spring.libr.library.services;

import ru.otus.spring.libr.library.entity.BookClient;

import java.util.Optional;

public interface BookClientService {

    BookClient save(BookClient bookClient);
    Optional<BookClient> getByAuthorAndBookName(String author, String bookName);
    void setClientToBook(BookClient bookClient, long clientId, String clientName);
    void returnBook();
}
