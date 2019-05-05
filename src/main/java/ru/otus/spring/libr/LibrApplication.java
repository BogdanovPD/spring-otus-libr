package ru.otus.spring.libr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;

@SpringBootApplication
public class LibrApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibrApplication.class, args);
    }

}
