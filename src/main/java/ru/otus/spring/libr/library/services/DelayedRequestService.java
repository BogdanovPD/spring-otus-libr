package ru.otus.spring.libr.library.services;

import ru.otus.spring.libr.dto.BookRequest;

public interface DelayedRequestService {

    String SEPARATOR = "#";

    void saveRequest(BookRequest bookRequest);
    String getByBookKey(String key);
    String getBookKey(String author, String bookName);
    String getClientKey(long clientId, String clientName);
    String getClientNameByKey(String clientKey);
    long getClientIdByKey(String clientKey);

}
