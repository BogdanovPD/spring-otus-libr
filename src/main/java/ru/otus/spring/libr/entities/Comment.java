package ru.otus.spring.libr.entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = "id")
@Builder
@Entity
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;
    @ManyToOne
    @JoinColumn(name = "book_id")
    protected Book book;
    protected String text;

    @Override
    public String toString() {
        return "Comment{" +
                "book=" + book +
                ", text='" + text + '\'' +
                '}';
    }
}
