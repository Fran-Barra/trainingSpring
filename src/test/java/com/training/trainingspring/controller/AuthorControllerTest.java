package com.training.trainingspring.controller;

import com.training.trainingspring.model.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class AuthorControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;
    private final String baseUrl = "/author";

    @Test
    public void TestAuthorController1_postNewAuthorWithCorrespondingArgumentsAndCorrectResult(){
    }

    @Test
    public void TestAuthorController2_postNewAuthorWithMissingNameAndGetBadRequestError(){

    }
    @Test
    public void TestAuthorController3_getAuthorWithExistingIDAndCorrectlyReturnTheAuthor(){
        ResponseEntity<Author> getResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, null, Author.class);
        assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    }
    @Test
    public void TestAuthorController4_getAuthorWithNonExistingIDAndGetNotFoundMessage(){
        ResponseEntity<Author> getResponse = restTemplate.exchange(baseUrl, HttpMethod.GET, null, Author.class);
        assertEquals(HttpStatus.NOT_FOUND, getResponse.getStatusCode());
    }
    @Test
    public void TestAuthorController5_updateAuthorWithCorrespondingArgumentsAndCorrectResult(){
    }
    @Test
    public void TestAuthorController6_updateAuthorWithNonExistingIDAndGetNotFound(){
    }
    //TODO: add test for not missing params in update and invalid params
    @Test
    public void TestAuthorController7_deleteAuthorWithExistingIDAndCorrectResult(){
    }
    @Test
    public void TestAuthorController8_deleteAuthorWithNonExistingIDAnGetNotFound(){
    }
}
