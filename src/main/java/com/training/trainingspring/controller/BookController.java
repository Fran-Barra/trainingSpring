package com.training.trainingspring.controller;

import com.training.trainingspring.dto.BookDTO;
import com.training.trainingspring.model.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(value = "complete", defaultValue = "false") boolean complete){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @PostMapping
    public ResponseEntity<Book> postNewAuthor(@RequestBody BookDTO bookDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getAuthor(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateAuthor(@PathVariable UUID id, @RequestBody BookDTO bookDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorByID(@PathVariable UUID id){
        return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
