package ru.otus.spring.libr.library.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "book_client")
public class BookClient {

    @Id
    @Column(nullable = false, unique = true)
    protected long bookId;
    protected long clientId;
    @Column(columnDefinition = "TIMESTAMP")
    protected LocalDateTime dateTime;
    @NotBlank
    protected String bookName;
    @NotBlank
    protected String bookAuthor;
    private String clientName;

}
