package ru.otus.spring.libr;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.otus.spring.libr.entities.User;
import ru.otus.spring.libr.repository.UserRepository;

@SpringBootApplication
@RequiredArgsConstructor
public class LibrApplication {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(LibrApplication.class, args);
        UserRepository userRepository = context.getBean("userRepository", UserRepository.class);
        PasswordEncoder passwordEncoder = context.getBean("passwordEncoder", PasswordEncoder.class);
        userRepository.deleteAll();
        userRepository.save(User.builder()
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123"))
                .role("ADMIN")
                .enabled(true)
                .build());
        userRepository.save(User.builder()
                .email("user@gmail.com")
                .password(passwordEncoder.encode("123"))
                .role("USER")
                .enabled(true)
                .build());
    }

}
