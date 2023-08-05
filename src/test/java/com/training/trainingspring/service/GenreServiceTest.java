package com.training.trainingspring.service;

import com.training.trainingspring.dto.GenreDTO;
import com.training.trainingspring.model.Genre;
import com.training.trainingspring.service.interfaces.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.annotation.DirtiesContext;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class GenreServiceTest {
    @Autowired
    private GenreService genreService;

    @Test
    void TestGenreService_crateGenre_1() {
        GenreDTO genreDTO = GenreDTO.builder().type("Fiction").build();
        Genre savedGenre = genreService.createGenre(genreDTO);
        assertEquals(savedGenre.getType(), genreDTO.getType());
    }

    @Test
    void TestGenreService_createGenre_2_crateAGenreOfTypeAlreadyExistingAndGetAnException(){
        GenreDTO genreDTO = GenreDTO.builder().type("Fiction").build();
        genreService.createGenre(genreDTO);
        assertThrowsExactly(DataIntegrityViolationException.class,()->genreService.createGenre(genreDTO));
    }


    @Test
    void TestGenreService_getGenreByID_1() throws ChangeSetPersister.NotFoundException {
        GenreDTO genreDTO = GenreDTO.builder().type("Adventure").build();

        Genre savedGenre = genreService.createGenre(genreDTO);
        Genre getedGenre = genreService.getGenreByID(savedGenre.getId());

        assertEquals(savedGenre.getType(), genreDTO.getType());
        assertEquals(savedGenre, getedGenre);
    }

    @Test
    void TestGenreService_getGenreByID_2_LookForANonExistentIdAndGetNotFoundError(){
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->genreService.getGenreByID(UUID.randomUUID()));
    }

    @Test
    void TestGenreService_getGenreByIDList_1() throws ChangeSetPersister.NotFoundException {
        List<GenreDTO> genreDTOList = new ArrayList<>();
        genreDTOList.add(GenreDTO.builder().type("TestList1").build());
        genreDTOList.add(GenreDTO.builder().type("TestList2").build());
        genreDTOList.add(GenreDTO.builder().type("TestList3").build());
        genreDTOList.add(GenreDTO.builder().type("TestList4").build());
        genreDTOList.add(GenreDTO.builder().type("TestList5").build());

        List<Genre> newGenres = new ArrayList<>(genreDTOList.size());
        genreDTOList.forEach(g->newGenres.add(genreService.createGenre(g)));

        List<UUID> uuids = new ArrayList<>();
        newGenres.forEach(g->uuids.add(g.getId()));
        List<Genre> gotGenres = genreService.getGenreByIDList(uuids);

        assertTrue(gotGenres.containsAll(newGenres));
    }

    @Test
    void TestGenreService_getGenreByIDList_2_lookForIdsThatDontExistAndGetNotFoundError(){
        List<UUID> uuids = new ArrayList<>();
        uuids.add(UUID.randomUUID());
        uuids.add(UUID.randomUUID());
        uuids.add(UUID.randomUUID());

        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->genreService.getGenreByIDList(uuids));
    }

    @Test
    void TestGenreService_getGenreByIDList_3_lookForIdsThatDontExistAndOthersThatExistAndGetAllTheOnesThatExist() throws ChangeSetPersister.NotFoundException {
        List<GenreDTO> genreDTOList = new ArrayList<>();
        genreDTOList.add(GenreDTO.builder().type("TestList1").build());
        genreDTOList.add(GenreDTO.builder().type("TestList2").build());
        genreDTOList.add(GenreDTO.builder().type("TestList3").build());
        genreDTOList.add(GenreDTO.builder().type("TestList4").build());
        genreDTOList.add(GenreDTO.builder().type("TestList5").build());

        List<Genre> newGenres = new ArrayList<>(genreDTOList.size());
        genreDTOList.forEach(g->newGenres.add(genreService.createGenre(g)));

        List<UUID> uuids = new ArrayList<>();
        newGenres.forEach(g->uuids.add(g.getId()));
        uuids.add(UUID.randomUUID());
        uuids.add(UUID.randomUUID());
        uuids.add(UUID.randomUUID());
        List<Genre> gotGenres = genreService.getGenreByIDList(uuids);

        assertTrue(gotGenres.containsAll(newGenres));
        assertEquals(newGenres.size(), gotGenres.size());
    }

    @Test
    void TestGenreService_updateGenre_1() throws ChangeSetPersister.NotFoundException {
        GenreDTO genreDTO = GenreDTO.builder().type("Fiction").build();
        Genre savedGenre = genreService.createGenre(genreDTO);
        Genre newData = genreService.updateGenre(GenreDTO.builder().type("Fan made").build(), savedGenre.getId());

        assertEquals(newData.getType(), "Fan made");
        assertEquals(newData.getId(), savedGenre.getId());
    }

    @Test
    void TestGenreService_updateGenre_2_updateAnNonExistingTypeAndGetAnNotFoundException(){
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class,
                ()-> genreService.updateGenre(GenreDTO.builder().type("Fan made").build(), UUID.randomUUID()));
    }

    @Test
    void TestGenreService_updateGenre_3_updateToAnExistingTypeAndGetAnException() {
        genreService.createGenre(GenreDTO.builder().type("Fiction").build());
        GenreDTO genreDTO = GenreDTO.builder().type("Drama").build();

        Genre savedGenre = genreService.createGenre(genreDTO);

        assertThrowsExactly(DataIntegrityViolationException.class,
                ()->genreService.updateGenre(GenreDTO.builder().type("Fiction").build(), savedGenre.getId()));
    }

    @Test
    void TestGenreService_deleteGenre_1_existingGenre() {
        GenreDTO genreDTO = GenreDTO.builder().type("Fiction").build();
        Genre savedGenre = genreService.createGenre(genreDTO);
        genreService.deleteGenre(savedGenre.getId());
        assertThrowsExactly(ChangeSetPersister.NotFoundException.class, ()->genreService.getGenreByID(savedGenre.getId()));
    }
}