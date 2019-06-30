package ru.otus.spring.libr.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    private long clientId;
    private String clientName;
    private String bookName;
    private String author;

}
