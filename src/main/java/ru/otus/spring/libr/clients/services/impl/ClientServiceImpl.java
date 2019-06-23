package ru.otus.spring.libr.clients.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.libr.clients.entity.Client;
import ru.otus.spring.libr.clients.repository.ClientRepository;
import ru.otus.spring.libr.clients.services.ClientService;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public Client saveClient(Client client) {
        return clientRepository.save(client);
    }

    @Override
    public long getClientsSize() {
        return clientRepository.count();
    }
}
