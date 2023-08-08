package com.training.trainingspring.service.interfaces;

import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.model.Author;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.UUID;

public interface AuthorService {
    /**
     *Given an id the method returns author found.
     * In case no author is found the method throw a not found error.
     */
    Author getAuthorByID(UUID id) throws ChangeSetPersister.NotFoundException;
    /**
     * Given the authorDTO a new author is created and stored considering the data.
     * @param authorDTO the data of the new genre
     * @return newAuthor
     */
    Author createAuthor(AuthorDTO authorDTO);
    /**
     * Given the authorDTO the data of the author with id will be created.
     * A data integrity violation error will rise if the constraints are broken.
     * A not found error will rise if the genre is not found.
     * @param authorDTO the new data
     * @param uuid the id of the author to modify
     * @return updatedAuthor
     */
    Author updateAuthor(AuthorDTO authorDTO, UUID uuid) throws ChangeSetPersister.NotFoundException;
    /**
     * Given the id the author with the corresponding id will be deleted.
     * if it does not exist then nothing is done.
     * @param id uuid of the author to delete
     */
    void deleteAuthor(UUID id);
}
