package com.training.trainingspring.service;


import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Genre;
import com.training.trainingspring.repository.GenreRepository;
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
public class GenreServiceImp implements GenreService {

    private final GenreRepository genreRepository;

    public GenreServiceImp(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    @Override
    public List<Genre> getGenreByIDList(List<UUID> ids) throws ChangeSetPersister.NotFoundException {
        val queryResult = genreRepository.findAllById(ids);
        if (queryResult.isEmpty()) throw new ChangeSetPersister.NotFoundException();
        return queryResult.stream().toList();
    }

    @Override
    public Genre getGenreByID(UUID id) throws ChangeSetPersister.NotFoundException {
        val queryResult = genreRepository.findById(id);
        if (queryResult.isEmpty()) throw new ChangeSetPersister.NotFoundException();
        return queryResult.get();
    }

    @Override
    public Genre createGenre(GenreDTO genreDTO) {
        Genre genre = Genre.builder().type(genreDTO.getType()).build();
        return genreRepository.save(genre);
    }

    @Override
    public Genre updateGenre(GenreDTO genreDTO, UUID uuid) throws ChangeSetPersister.NotFoundException {
        Optional<Genre> genreToMod = genreRepository.findById(uuid);
        if (genreToMod.isEmpty()) throw new ChangeSetPersister.NotFoundException();

        Genre genre = genreToMod.get();
        genre.setType(genreDTO.getType());
        return genreRepository.save(genre);
    }

    @Override
    public void deleteGenre(UUID id) {
        genreRepository.deleteById(id);
    }
}
