package com.training.trainingspring.service.interfaces;

import com.training.trainingspring.dto.BookBaseDTO;
import com.training.trainingspring.dto.BookDTO;
import com.training.trainingspring.model.Book;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface BookService {
    /**
     * The method return all the existing books.
     *
     * @return the list of books
     */
    List<BookBaseDTO> getAllBooks();
    /**
     * The method return the book with data of the author included
     * @return the list of books with author
     */
    List<Book> getAllBooksWithAuthor();
    /**
     *Given an id the method returns the book found.
     * In case no book is found the method throw a not found error.
     */
    Book getBookByID(UUID id) throws ChangeSetPersister.NotFoundException;
    /**
     * Given the bookDTO a new book is created and stored considering the data.
     * @param bookDTO the data of the new genre
     * @return newBook
     */
    Book createBook(BookDTO bookDTO) throws ChangeSetPersister.NotFoundException;
    /**
     * Given the bookDTO the data of the book with id will be updated.
     * A data integrity violation error will rise if the constraints are broken.
     * A not found error will rise if the genre is not found.
     * @param bookDTO the new data
     * @param uuid the id of the book to modify
     * @return updatedBook
     */
    Book updateBook(BookDTO bookDTO, UUID uuid) throws ChangeSetPersister.NotFoundException;
    /**
     * Given the id the book with the corresponding id will be deleted.
     * if it does not exist then nothing is done.
     * @param id uuid of the book to delete
     */
    void deleteBook(UUID id);
}
