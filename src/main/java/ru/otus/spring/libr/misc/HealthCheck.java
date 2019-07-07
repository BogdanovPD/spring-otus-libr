package ru.otus.spring.libr.misc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import ru.otus.spring.libr.library.repository.BookClientRepository;

@Component
@RequiredArgsConstructor
public class HealthCheck implements HealthIndicator {

    private final BookClientRepository bookClientRepository;

    @Override
    public Health health() {
        try {
            bookClientRepository.count();
        } catch (Exception ex) {
            return Health.down().withException(ex).build();
        }
        return Health.up().build();
    }
}
