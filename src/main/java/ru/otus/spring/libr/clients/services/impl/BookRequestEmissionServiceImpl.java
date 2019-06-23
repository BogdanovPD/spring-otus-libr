package ru.otus.spring.libr.clients.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.clients.dto.BookRequest;
import ru.otus.spring.libr.clients.services.BookRequestEmissionService;
import ru.otus.spring.libr.clients.services.ClientService;
import ru.otus.spring.libr.randoms.RandomNameUtil;

import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookRequestEmissionServiceImpl implements BookRequestEmissionService {

    private final ClientService clientService;

    @Override
    @Scheduled(fixedDelay = 10000)
    public void emitBookRequest() {
        long clientsSize = clientService.getClientsSize();
        if (clientsSize < 1) {
            return;
        }
        long id = clientsSize == 1 ? 1 : ThreadLocalRandom.current().nextLong(1, clientsSize);
        BookRequest bookRequest = BookRequest.builder()
                .clientId(id)
                .bookName(RandomNameUtil.getRandomBook())
                .build();
        log.info("New book request: " + bookRequest);
        log.info("===================================================================================================");
    }
}
