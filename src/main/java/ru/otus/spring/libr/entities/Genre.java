package ru.otus.spring.libr.entities;

import lombok.Builder;

public class Genre extends NamedEntity {

    @Builder
    public Genre(long id, String name) {
        super(id, name);
    }

}
