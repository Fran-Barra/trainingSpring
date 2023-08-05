package com.training.trainingspring.service.interfaces;

import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Genre;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;
import java.util.UUID;

public interface GenreService {
    /**
    *Given a list of ids the method returns all the genres found.
    * In case no genre is found the method throw a not found error.
    */
    List<Genre> getGenreByIDList(List<UUID> ids) throws ChangeSetPersister.NotFoundException;
    /**
     *Given an id the method returns genre found.
     * In case no genre is found the method throw a not found error.
     */
    Genre getGenreByID(UUID id) throws ChangeSetPersister.NotFoundException;

    /**
     * Given the genreDTO a new genre is created and stored considering the data.
     * A conflict error will rise if the type already exist.
     * @param genreDTO the data of the new genre
     * @return newGenre
     */
    Genre createGenre(GenreDTO genreDTO);

    /**
     * Given the genreDTO the data of the genre with id will be created.
     * A conflict error will rise if the type already exist.
     * A not found error will rise if the genre is not found.
     * @param genreDTO the new data
     * @param uuid the id of the genre to modify
     * @return updatedGenre
     */
    Genre updateGenre(GenreDTO genreDTO, UUID uuid) throws ChangeSetPersister.NotFoundException;

    /**
     * Given the id the genre with the corresponding id will be deleted.
     * if it does not exist then nothing is done.
     * @param id
     */
    void deleteGenre(UUID id);


}
