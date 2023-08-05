package com.training.trainingspring.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
@EqualsAndHashCode
public class Genre {
    private UUID id;
    private String type;
}
