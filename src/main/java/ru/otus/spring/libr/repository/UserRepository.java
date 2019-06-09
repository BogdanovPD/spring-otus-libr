package ru.otus.spring.libr.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.entities.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> getUserByEmail(String email);

}
