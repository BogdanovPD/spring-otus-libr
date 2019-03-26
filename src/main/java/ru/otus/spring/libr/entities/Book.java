package ru.otus.spring.libr.entities;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"id", "comments"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue
    protected long id;
    protected String name;

    @ManyToOne
    @JoinColumn(name = "author_id")
    protected Author author;
    @ManyToOne
    @JoinColumn(name = "genre_id")
    protected Genre genre;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    protected List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return "Book{" +
                "author=" + author +
                ", genre=" + genre +
                ", name=" + name + '}';
    }
}
