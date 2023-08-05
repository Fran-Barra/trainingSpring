package com.training.trainingspring.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GenreDTO {
    private UUID id;
    private String type;
}
