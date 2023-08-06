package com.training.trainingspring.service;


import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.model.Author;
import com.training.trainingspring.service.interfaces.AuthorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.annotation.DirtiesContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;

    @Test
    void TestAuthorService_crateAuthor_1() {
        AuthorDTO authorDTO = AuthorDTO.builder().name("Jorge").build();
        Author savedAuthor = authorService.createAuthor(authorDTO);
        assertEquals(savedAuthor.getName(), authorDTO.getName());
    }
    @Test
    void TestAuthorService_getAuthorByID_1() throws ChangeSetPersister.NotFoundException {
        AuthorDTO authorDTO = AuthorDTO.builder().name("Jorge").build();

        Author savedAuthor = authorService.createAuthor(authorDTO);
        Author gotAuthor = authorService.getAuthorByID(savedAuthor.getId());

        assertEquals(savedAuthor.getName(), authorDTO.getName());
        assertEquals(savedAuthor, gotAuthor);
    }

    @Test
    void TestAuthorService_getAuthorByID_2_LookForANonExistentIdAndGetNotFoundError(){
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->authorService.getAuthorByID(UUID.randomUUID()));
    }
    @Test
    void TestAuthorService_updateAuthor_1() throws ChangeSetPersister.NotFoundException {
        AuthorDTO authorDTO = AuthorDTO.builder().name("Emilio").build();
        Author savedAuthor = authorService.createAuthor(authorDTO);
        Author newData = authorService.updateAuthor(AuthorDTO.builder().name("Jorge").build(), savedAuthor.getId());

        assertEquals(newData.getName(), "Jorge");
        assertEquals(newData.getId(), savedAuthor.getId());
    }

    @Test
    void TestAuthorService_updateAuthor_2_updateAnNonExistingTypeAndGetAnNotFoundException(){
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class,
                ()-> authorService.updateAuthor(AuthorDTO.builder().name("Pedro").build(), UUID.randomUUID()));
    }

    @Test
    void TestAuthorService_deleteAuthor_1_existingAuthor() {
        AuthorDTO authorDTO = AuthorDTO.builder().name("Pedro").build();
        Author savedAuthor = authorService.createAuthor(authorDTO);
        authorService.deleteAuthor(savedAuthor.getId());
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->authorService.getAuthorByID(savedAuthor.getId()));
    }
}
