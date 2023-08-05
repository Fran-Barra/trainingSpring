package com.training.trainingspring.controller;

import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.model.Author;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {
    @PostMapping
    public ResponseEntity<Author> postNewAuthor(@RequestBody AuthorDTO authorDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorByID(@PathVariable UUID id){
        return  ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }
}
