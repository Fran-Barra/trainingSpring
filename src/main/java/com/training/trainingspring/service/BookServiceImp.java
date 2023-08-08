package com.training.trainingspring.service;

import com.training.trainingspring.dto.BookDTO;
import com.training.trainingspring.model.Author;
import com.training.trainingspring.model.Book;
import com.training.trainingspring.model.Genre;
import com.training.trainingspring.repository.BookRepository;
import com.training.trainingspring.service.interfaces.AuthorService;
import com.training.trainingspring.service.interfaces.BookService;
import com.training.trainingspring.service.interfaces.GenreService;
import lombok.val;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class BookServiceImp implements BookService {

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final GenreService genreService;

    public BookServiceImp(BookRepository bookRepository, AuthorService authorService, GenreService genreService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.genreService = genreService;
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAllWithAuthorId();
    }

    @Override
    public List<Book> getAllBooksWithAuthor() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookByID(UUID id) throws ChangeSetPersister.NotFoundException {
        val queryResult = bookRepository.findById(id);
        if (queryResult.isEmpty()) throw new ChangeSetPersister.NotFoundException();
        return queryResult.get();
    }

    @Override
    public Book createBook(BookDTO bookDTO) throws ChangeSetPersister.NotFoundException {
        Author author = authorService.getAuthorByID(bookDTO.getAuthor());
        List<Genre> genres = genreService.getGenreByIDList(bookDTO.getGenres());
        return bookRepository.save(
                Book.builder().title(bookDTO.getTitle()).author(author).
                        bookYear(bookDTO.getBookYear()).genres(genres).build()
        );
    }

    @Override
    public Book updateBook(BookDTO bookDTO, UUID uuid) throws ChangeSetPersister.NotFoundException {
        Optional<Book> bookToUpdate = bookRepository.findById(uuid);
        if (bookToUpdate.isEmpty()) throw new ChangeSetPersister.NotFoundException();
        Book book = bookToUpdate.get();

        book.setTitle(bookDTO.getTitle());
        book.setBookYear(bookDTO.getBookYear());

        book.setAuthor(authorService.getAuthorByID(bookDTO.getAuthor()));
        List<Genre> genresFound = genreService.getGenreByIDList(bookDTO.getGenres());
        if (genresFound.size() != bookDTO.getGenres().size()) throw new ChangeSetPersister.NotFoundException();
        book.setGenres(genresFound);

        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(UUID id) {
        bookRepository.deleteById(id);
    }
}
