package com.training.trainingspring.model;

import lombok.*;

import java.time.Year;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Book {
    private UUID id;
    private String title;
    private Year year;
    private UUID genre;
}
