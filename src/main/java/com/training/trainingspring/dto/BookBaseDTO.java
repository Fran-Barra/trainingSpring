package com.training.trainingspring.dto;

import jakarta.persistence.ColumnResult;
import jakarta.persistence.ConstructorResult;
import jakarta.persistence.SqlResultSetMapping;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@SqlResultSetMapping(
        name = "BookBaseDTOMapping",
        classes = @ConstructorResult(
                targetClass = BookBaseDTO.class,
                columns = {
                        @ColumnResult(name = "id", type = UUID.class),
                        @ColumnResult(name = "title", type = String.class),
                        @ColumnResult(name = "book_year", type = int.class),
                        @ColumnResult(name = "author_id", type = UUID.class)
                }
        )
)
public class BookBaseDTO{
    private UUID id;
    private String title;
    private int bookYear;
    private UUID authorID;
}
