package ru.otus.spring.libr.library.repository;

import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.libr.library.entity.DelayedRequest;

public interface DelayedRequestRepository extends CrudRepository<DelayedRequest, String> {

}
