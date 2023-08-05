package com.training.trainingspring.controller;

import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Genre;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/genre")
public class GenreController {

    @PostMapping
    public ResponseEntity<Genre> postNewGenre(@RequestBody GenreDTO genreDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable UUID id, @RequestBody GenreDTO genreDTO){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewGenre(@PathVariable UUID id){
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).build();
    }


}
