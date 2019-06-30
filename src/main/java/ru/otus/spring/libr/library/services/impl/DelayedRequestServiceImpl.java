package ru.otus.spring.libr.library.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.spring.libr.dto.BookRequest;
import ru.otus.spring.libr.library.entity.DelayedRequest;
import ru.otus.spring.libr.library.repository.DelayedRequestRepository;
import ru.otus.spring.libr.library.services.DelayedRequestService;

import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DelayedRequestServiceImpl implements DelayedRequestService {

    private final DelayedRequestRepository delayedRequestRepository;

    @Override
    public void saveRequest(BookRequest bookRequest) {
        log.info("===================================================================================================");
        log.info("Saving of the request " + bookRequest);
        String key = getBookKey(bookRequest.getAuthor(), bookRequest.getBookName());
        Optional<DelayedRequest> delayedRequestOpt = delayedRequestRepository.findById(key);
        DelayedRequest delayedRequest;
        if (delayedRequestOpt.isEmpty()) {
            delayedRequest = DelayedRequest.builder()
                    .requestedBook(key)
                    .build();
            delayedRequestRepository.save(delayedRequest);
        } else {
            delayedRequest = delayedRequestOpt.get();
            delayedRequest.getClients().add(getClientKey(bookRequest.getClientId(), bookRequest.getClientName()));
        }
        log.info("Request has been saved");
        log.info("===================================================================================================");
    }

    @Override
    public String getByBookKey(String key) {
        Optional<DelayedRequest> delayedRequestOpt = delayedRequestRepository.findById(key);
        if (delayedRequestOpt.isEmpty()) {
            return "";
        }
        DelayedRequest delayedRequest = delayedRequestOpt.get();

        Set<String> clients = delayedRequest.getClients();
        String clientKey = "";
        if (clients.size() > 0) {
            clientKey = clients.iterator().next();
            clients.remove(clientKey);
        }
        return clientKey;
    }

    @Override
    public String getBookKey(String author, String bookName) {
        return author.concat("#").concat(bookName);
    }

    @Override
    public String getClientKey(long clientId, String clientName) {
        return String.valueOf(clientId).concat(SEPARATOR).concat(clientName);
    }

    @Override
    public String getClientNameByKey(String clientKey) {
        return clientKey.split(SEPARATOR)[1];
    }

    @Override
    public long getClientIdByKey(String clientKey) {
        return Long.parseLong(clientKey.split(SEPARATOR)[0]);
    }
}
