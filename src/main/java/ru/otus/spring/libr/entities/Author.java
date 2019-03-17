package ru.otus.spring.libr.entities;

import lombok.Builder;

public class Author extends NamedEntity {

    @Builder
    public Author(long id, String name) {
        super(id, name);
    }

}
