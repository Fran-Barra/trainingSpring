package com.training.trainingspring.controller;

import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.model.Author;
import com.training.trainingspring.service.interfaces.AuthorService;
import lombok.val;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/author")
public class AuthorController {
    private final AuthorService authorService;
    public AuthorController(AuthorService authorService) {this.authorService = authorService;}

    @PostMapping
    public ResponseEntity<Author> postNewAuthor(@RequestBody AuthorDTO authorDTO){
        val author = authorService.createAuthor(authorDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(author);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable UUID id){
        try {
            val author = authorService.getAuthorByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable UUID id, @RequestBody AuthorDTO authorDTO){
        try {
            val author = authorService.updateAuthor(authorDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(author);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthorByID(@PathVariable UUID id){
        authorService.deleteAuthor(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
