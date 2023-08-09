package com.training.trainingspring.controller;

import com.training.trainingspring.dto.BookDTO;
import com.training.trainingspring.model.Book;
import com.training.trainingspring.service.interfaces.BookService;
import lombok.val;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {this.bookService = bookService;}

    @GetMapping
    public ResponseEntity<List<?>> getAllBooks(@RequestParam(value = "complete", defaultValue = "false") boolean complete){
        List<?> book;
        if (complete) book = bookService.getAllBooksWithAuthor();
        else book = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }
    @PostMapping
    public ResponseEntity<Book> postNewBook(@RequestBody BookDTO bookDTO) {
        try {
            Book book = bookService.createBook(bookDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(book);
        } catch (Exception ex) {
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable UUID id){
        try {
            val book = bookService.getBookByID(id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id, @RequestBody BookDTO bookDTO){
        try {
            val book = bookService.updateBook(bookDTO, id);
            return ResponseEntity.status(HttpStatus.OK).body(book);
        }catch (Exception ex){
            if (ex instanceof ChangeSetPersister.NotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookByID(@PathVariable UUID id){
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
