package ru.otus.spring.libr.clients.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.clients.entity.Client;

public interface ClientRepository extends CrudRepository<Client, Long> {



}
