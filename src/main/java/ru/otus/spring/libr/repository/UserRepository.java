package ru.otus.spring.libr.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.otus.spring.libr.entities.User;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {

    Optional<User> getUserByEmail(String email);

}
