package com.training.trainingspring.service;


import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.dto.BookDTO;
import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Author;
import com.training.trainingspring.model.Book;
import com.training.trainingspring.model.Genre;
import com.training.trainingspring.service.interfaces.AuthorService;
import com.training.trainingspring.service.interfaces.BookService;
import com.training.trainingspring.service.interfaces.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class BookServiceTest {

    @Autowired
    private BookService bookService;
    @Autowired
    private AuthorService authorService;
    @Autowired
    private GenreService genreService;

    @Test
    public void TestBookService_createBook_1_CreateBookCorrectlyWithAllData() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("Fiction").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        assertEquals(savedBook.getTitle(), "Book1");
        assertEquals(savedBook.getBookYear(), 2000);
        assertEquals(savedBook.getAuthor().getId(), savedAuthor.getId());
        assertEquals(savedBook.getGenres().get(0).getId(), genres.get(0));
    }

    @Test
    public void TestBookService_createBook_2_CreateCorrectlyWithManyGenres() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("G1").build()).getId());
        genres.add(genreService.createGenre(GenreDTO.builder().type("G2").build()).getId());
        genres.add(genreService.createGenre(GenreDTO.builder().type("G3").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        assertEquals(savedBook.getTitle(), "Book1");
        assertEquals(savedBook.getBookYear(), 2000);
        assertEquals(savedBook.getAuthor().getId(), savedAuthor.getId());

        List<UUID> genresIDsSaved = new ArrayList<>();
        savedBook.getGenres().forEach(g->genresIDsSaved.add(g.getId()));
        assertTrue(genresIDsSaved.containsAll(genres));
    }

    @Test
    public void TestBookService_createBook_3_WithNonExistentGenreAndGetError() {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(UUID.randomUUID());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();

        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->bookService.createBook(bookDTO));
    }

    @Test
    public void TestBookService_createBook_4_WithNonExistentAuthorAndGetError() {
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("Fiction").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(UUID.randomUUID()).
                genres(genres).
                build();

        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->bookService.createBook(bookDTO));
    }

    @Test
    public void TestBookService_getAllBooksWithAuthor_1_NotEmpty() throws ChangeSetPersister.NotFoundException {
        Author author1 = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        Author author2 = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("Fiction").build()).getId());
        genres.add(genreService.createGenre(GenreDTO.builder().type("Light Novel").build()).getId());
        genres.add(genreService.createGenre(GenreDTO.builder().type("Terror").build()).getId());

        List<UUID> booksUUID = new ArrayList<>();

        booksUUID.add(bookService.createBook(BookDTO.builder().title("Book1").
                bookYear(2000).author(author1.getId()).
                genres(genres.subList(0,2)).build())
                .getId());

        booksUUID.add(bookService.createBook(BookDTO.builder().title("Book2").
                bookYear(2000).author(author2.getId()).
                genres(genres.subList(0,1)).build())
                .getId());

        booksUUID.add(bookService.createBook(BookDTO.builder().title("Book3").
                bookYear(2000).author(author2.getId()).
                genres(genres.subList(1,2)).build())
                .getId());


        List<Book> books = bookService.getAllBooksWithAuthor();

        assertEquals(books.size(), booksUUID.size());
        List<UUID> gotUUIDs = new ArrayList<>(books.size());
        books.forEach(b->gotUUIDs.add(b.getId()));
        assertTrue(gotUUIDs.containsAll(booksUUID));
    }

    @Test
    public void TestBookService_getAllBooksWithAuthor_2_Empty() {
        List<Book> books = bookService.getAllBooksWithAuthor();
        assertTrue(books.isEmpty());
    }

    @Test
    public void TestBookService_getBookByID_1_FindExistingBook() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("Fiction").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        Book gotBook = bookService.getBookByID(savedBook.getId());
        assertEquals(gotBook.getId(), savedBook.getId());
    }

    @Test
    public void TestBookService_getBookId_2_GetNonExistentBookWithNotFoundException() {
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->bookService.getBookByID(UUID.randomUUID()));
    }

    @Test
    public void TestBookService_updateBook_1_WithValidDataCorrectly() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("G1").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        genres.add(genreService.createGenre(GenreDTO.builder().type("G2").build()).getId());
        genres.add(genreService.createGenre(GenreDTO.builder().type("G3").build()).getId());

        bookDTO = BookDTO.builder().
                title("Book2").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();

        Book gotBook = bookService.updateBook(bookDTO, savedBook.getId());

        assertEquals(savedBook.getId(),gotBook.getId());
        assertEquals( "Book2", gotBook.getTitle());
        assertEquals(genres.size(), gotBook.getGenres().size());
        //TODO: to improve test could try comparing all UUID of the genres
    }

    @Test
    public void TestBookService_updateBook_2_NonExistentBookGetNotFoundError() {
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->
                bookService.updateBook(BookDTO.builder().build(), UUID.randomUUID()));
    }

    @Test
    public void TestBookService_updateBook_3_UpdateWithNonExistentGenreAndGetError() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("G1").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        genres.add(Genre.builder().id(UUID.randomUUID()).type("G2").build().getId());

        bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();

        BookDTO finalBookDTO = bookDTO;
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->
                bookService.updateBook(finalBookDTO, savedBook.getId()));
    }

    @Test
    public void TestBookService_updateBook_4_UpdateWithNonExistentAuthorAndGetError() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("G1").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(UUID.randomUUID()).
                genres(genres).
                build();

        BookDTO finalBookDTO = bookDTO;
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class,
                ()->bookService.updateBook(finalBookDTO, savedBook.getId()));
    }

    @Test void TestBookService_deleteBookByID_1_DeleteCorrectly() throws ChangeSetPersister.NotFoundException {
        Author savedAuthor = authorService.createAuthor(AuthorDTO.builder().name("Jorge").build());
        List<UUID> genres = new ArrayList<>();
        genres.add(genreService.createGenre(GenreDTO.builder().type("Fiction").build()).getId());

        BookDTO bookDTO = BookDTO.builder().
                title("Book1").
                bookYear(2000).
                author(savedAuthor.getId()).
                genres(genres).
                build();
        Book savedBook = bookService.createBook(bookDTO);

        bookService.deleteBook(savedBook.getId());
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->bookService.getBookByID(savedBook.getId()));
    }
}
