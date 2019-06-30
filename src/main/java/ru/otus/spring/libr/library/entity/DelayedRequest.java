package ru.otus.spring.libr.library.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class DelayedRequest {

    @Id
    private String requestedBook;
    @ElementCollection
    @CollectionTable(name = "delayed_request_clients",
            joinColumns = @JoinColumn(name = "requested_book"))
    @Column(name = "client")
            @Builder.Default
    Set<String> clients = new HashSet<>();

}
