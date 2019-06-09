package ru.otus.spring.libr;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
public class LibrApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrApplication.class, args);
    }

}
