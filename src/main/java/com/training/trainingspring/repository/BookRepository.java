package com.training.trainingspring.repository;

import com.training.trainingspring.dto.BookBaseDTO;

import com.training.trainingspring.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    //@Query(value = "SELECT TITLE FROM book", nativeQuery = true)
    //List<Book> findAllBase();

}
