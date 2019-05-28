package ru.otus.spring.libr.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {"id", "comments"})
@ToString(exclude = {"id", "comments"})
@Builder
@Document(collection = "libr")
public class Book {

    @Id
    private String id;

    private String author;
    private String genre;
    private String name;

    private List<String> comments = new ArrayList<>();

}
