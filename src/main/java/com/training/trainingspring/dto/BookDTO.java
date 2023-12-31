package com.training.trainingspring.dto;

import lombok.*;

import java.time.Year;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class BookDTO {
    private String title;
    private int bookYear;
    private UUID author;
    private List<UUID> genres;
}
