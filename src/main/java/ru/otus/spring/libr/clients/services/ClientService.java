package ru.otus.spring.libr.clients.services;

import ru.otus.spring.libr.clients.entity.Client;

public interface ClientService {

    Client saveClient(Client client);
    long getClientsSize();
    String getNameById(long id);

}
