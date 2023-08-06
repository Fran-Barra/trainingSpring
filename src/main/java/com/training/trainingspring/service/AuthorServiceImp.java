package com.training.trainingspring.service;

import com.training.trainingspring.dto.AuthorDTO;
import com.training.trainingspring.model.Author;
import com.training.trainingspring.repository.AuthorRepository;
import com.training.trainingspring.service.interfaces.AuthorService;
import lombok.val;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;
import java.util.UUID;

@Service
@Validated
public class AuthorServiceImp implements AuthorService {
    private final AuthorRepository authorRepository;
    public AuthorServiceImp(AuthorRepository authorRepository) {this.authorRepository = authorRepository;}

    @Override
    public Author getAuthorByID(UUID id) throws ChangeSetPersister.NotFoundException {
        val queryResult = authorRepository.findById(id);
        if (queryResult.isEmpty()) throw new ChangeSetPersister.NotFoundException();
        return queryResult.get();
    }

    @Override
    public Author createAuthor(AuthorDTO authorDTO) {
        return authorRepository.save(Author.builder().name(authorDTO.getName()).build());
    }

    @Override
    public Author updateAuthor(AuthorDTO authorDTO, UUID uuid) throws ChangeSetPersister.NotFoundException {
        Optional<Author> authorToMod = authorRepository.findById(uuid);
        if (authorToMod.isEmpty()) throw new ChangeSetPersister.NotFoundException();

        Author genre = authorToMod.get();
        genre.setName(authorDTO.getName());
        return authorRepository.save(genre);
    }

    @Override
    public void deleteAuthor(UUID id) {
        authorRepository.deleteById(id);
    }
}
