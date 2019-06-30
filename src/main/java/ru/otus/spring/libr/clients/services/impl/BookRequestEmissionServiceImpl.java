package ru.otus.spring.libr.clients.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.Libr;
import ru.otus.spring.libr.dto.BookRequest;
import ru.otus.spring.libr.clients.services.BookRequestEmissionService;
import ru.otus.spring.libr.clients.services.ClientService;
import ru.otus.spring.libr.util.RandomNameUtil;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookRequestEmissionServiceImpl implements BookRequestEmissionService {

    private final ClientService clientService;
    private final Libr libr;

    @Override
    @Scheduled(fixedDelay = 1000)
    public void emitBookRequest() {
        long clientsSize = clientService.getClientsSize();
        if (clientsSize < 1) {
            return;
        }
        long id = clientsSize == 1 ? 1 : ThreadLocalRandom.current().nextLong(1, clientsSize);
        BookRequest bookRequest = BookRequest.builder()
                .clientId(id)
                .clientName(clientService.getNameById(id))
                .bookName(RandomNameUtil.getRandomBook())
                .author(RandomNameUtil.getRandomAuthor())
                .build();
        libr.bookRequest(bookRequest);
        log.info("New book request: " + bookRequest);
        log.info("===================================================================================================");
    }
}
