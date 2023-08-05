package com.training.trainingspring.controller;

import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Genre;
import com.training.trainingspring.service.interfaces.GenreService;
import lombok.val;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/genre")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService){
        this.genreService = genreService;
    }

    @PostMapping
    public ResponseEntity<Genre> postNewGenre(@RequestBody GenreDTO genreDTO){
        val genre = genreService.createGenre(genreDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(genre);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Genre> getGenre(@PathVariable UUID id){
        try {
            val genre = genreService.getGenreByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(genre);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Genre> updateGenre(@PathVariable UUID id, @RequestBody GenreDTO genreDTO){
        try {
            val genre = genreService.updateGenre(genreDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(genre);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNewGenre(@PathVariable UUID id){
        genreService.deleteGenre(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
