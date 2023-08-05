package com.training.trainingspring.model;

import lombok.*;

import java.util.UUID;


@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Author {
    private UUID uuid;
    private String name;
}
