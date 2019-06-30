package ru.otus.spring.libr.clients.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.otus.spring.libr.clients.entity.Client;
import ru.otus.spring.libr.clients.repository.ClientRepository;
import ru.otus.spring.libr.clients.services.ClientService;

import java.util.Optional;

@Slf4j
@Service
@Transactional
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

    @Override
    public String getNameById(long id) {
        Optional<Client> clientOptional = clientRepository.findClientById(id);
        if (clientOptional.isEmpty()) {
            String msg = "Cannot find client by id " + id;
            log.error(msg);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, msg);
        }
        return clientOptional.get().getName();
    }
}
