package ru.otus.spring.libr.entities;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode(callSuper = true)
@Getter
public class Book extends NamedEntity {

    private Author author;
    private Genre genre;

    @Builder
    public Book(long id, String name, Author author, Genre genre) {
        super(id, name);
        this.author = author;
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", genre=" + genre +
                ", name=" + name + '}';
    }
}
