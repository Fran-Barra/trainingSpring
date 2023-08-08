package com.training.trainingspring.repository;

import com.training.trainingspring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    @Query("SELECT b FROM Book b LEFT JOIN FETCH b.author")
    List<Book> findAllWithAuthorId();
}
