package ru.otus.spring.libr.clients.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.clients.entity.Client;

import java.util.Optional;

public interface ClientRepository extends CrudRepository<Client, Long> {

    Optional<Client> findClientById(long id);

}
