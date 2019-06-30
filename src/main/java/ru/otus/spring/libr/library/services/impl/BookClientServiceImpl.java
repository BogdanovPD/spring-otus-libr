package ru.otus.spring.libr.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.libr.library.entity.BookClient;
import ru.otus.spring.libr.library.repository.BookClientRepository;
import ru.otus.spring.libr.library.services.BookClientService;
import ru.otus.spring.libr.library.services.DelayedRequestService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BookClientServiceImpl implements BookClientService {

    private final BookClientRepository bookClientRepository;
    private final DelayedRequestService delayedRequestService;

    @Override
    public BookClient save(BookClient bookClient) {
        bookClient = bookClientRepository.save(bookClient);
        updateClientIfWaiting(bookClient);
        return bookClient;
    }

    private void updateClientIfWaiting(BookClient bookClient) {
        String key = delayedRequestService.getBookKey(bookClient.getBookAuthor(), bookClient.getBookName());
        String clientKey = delayedRequestService.getByBookKey(key);
        if (clientKey != null && !clientKey.isBlank()) {
            log.info("===============================================================================================");
            log.info("Delayed request occurred for book [author=" + bookClient.getBookAuthor() + ", name=" + bookClient.getBookName() + "]");
            long clientId = delayedRequestService.getClientIdByKey(clientKey);
            log.info("===============================================================================================");
            String clientName = delayedRequestService.getClientNameByKey(clientKey);
            setClientToBook(bookClient, clientId, clientName);
        }
    }

    @Override
    public Optional<BookClient> getByAuthorAndBookName(String author, String bookName) {
        List<BookClient> books = bookClientRepository.getAllByBookAuthorAndBookNameAndClientIdEquals(author, bookName, 0);
        if (books.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(books.get(0));
    }

    @Override
    public void setClientToBook(BookClient bookClient, long clientId, String clientName) {
        bookClient.setClientId(clientId);
        bookClient.setClientName(clientName);
        bookClient.setDateTime(LocalDateTime.now());
        bookClientRepository.save(bookClient);
        log.info("===================================================================================================");
        log.info("Client [id=" + clientId + ", name=" + clientName + "] has taken a book [id=" + bookClient.getBookId() +
                ", name=" + bookClient.getBookName() + ", author=" + bookClient.getBookAuthor() + "]");
        log.info("===================================================================================================");
    }

    @Override
    @Scheduled(fixedDelay = 30000)
    public void returnBook() {
        List<BookClient> bookClients = bookClientRepository.findAllByClientIdIsNot(0);
        if (bookClients.isEmpty()) {
            return;
        }
        BookClient bookClient = bookClients.get(0);
        log.info("===================================================================================================");
        log.info("Client [id=" + bookClient.getBookId() + ", name=" + bookClient.getClientName() +
                "] has returned a book [id=" + bookClient.getBookId() + ", name=" + bookClient.getBookName() +
                ", author=" + bookClient.getBookAuthor() + "]");
        bookClient.setClientId(0);
        bookClient.setDateTime(null);
        bookClient.setClientName(null);
        log.info("===================================================================================================");
        updateClientIfWaiting(bookClient);
    }
}
