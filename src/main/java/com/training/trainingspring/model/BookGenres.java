package com.training.trainingspring.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(columnNames = {"book", "genre"})
)
@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class BookGenres {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID book;
    private UUID genre;
}
