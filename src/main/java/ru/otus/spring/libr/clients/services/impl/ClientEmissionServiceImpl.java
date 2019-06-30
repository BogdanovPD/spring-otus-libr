package ru.otus.spring.libr.clients.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.clients.entity.Client;
import ru.otus.spring.libr.clients.services.ClientEmissionService;
import ru.otus.spring.libr.clients.services.ClientService;
import ru.otus.spring.libr.util.RandomNameUtil;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientEmissionServiceImpl implements ClientEmissionService {

    private final ClientService clientService;

    @Override
    @Scheduled(fixedDelay = 10000)
    public void emitClient() {
        Client client = clientService.saveClient(Client.builder()
                .name(RandomNameUtil.getRandomClient())
                .build());
        log.info("New client: " + client);
        log.info("===================================================================================================");
    }
}
