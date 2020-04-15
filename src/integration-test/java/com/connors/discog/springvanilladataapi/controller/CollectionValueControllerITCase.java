package com.connors.discog.springvanilladataapi.controller;

import com.connors.discog.springvanilladataapi.config.TestDataProvider;
import com.google.gson.JsonObject;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalToObject;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;

@DisplayName("Integration Tests of the CollectionValue CRUD REST endpoints")
public class CollectionValueControllerITCase extends CrudControllerITCase {

    @Test
    @DisplayName("GET a list with 3 CollectionValues")
    public void whenGETfindAll_thenGetListOf3CollctionValues() {
        //when
        ValidatableResponse response = given()
                .when()
                .get( baseURL + "/collectionValues/")

                .prettyPeek()
                .then();

        //then
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("size()", greaterThanOrEqualTo(2));
    }

    @Test
    @DisplayName("GET an CollectionValue by Id")
    public void givenCollectionValueId_thenGetSingleCollectionValue() {
        //given
        Long authorId = 2L;

        //when
        ValidatableResponse response = given()
                .when()
                .get(baseURL + "/collectionValues/" + authorId)

                .prettyPeek()
                .then();

        //then restassured need to compare against float
        response.statusCode(HttpStatus.OK.value())
                .contentType("application/json")
                .body("id", equalTo(2))
                .body("creationDateTime", equalTo("2020-01-26T10:02:50"))
                .body("maximum", equalTo(33.23f))
                .body("minimum", equalTo(12.30f))
                .body("median", equalToObject(24.50f))
               ;
    }

    @Test
    @DisplayName("POST an collectionValues to create it")
    public void givenCollectionValues_whenPOSTSave_thenGetSavedCollectionValues(){
        //given
        JsonObject collectionValueJson = TestDataProvider.getCollectionValueJson();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(collectionValueJson.toString())

                .when()
                .post(baseURL + "/collectionValues/")

                .prettyPeek()
                .then();

        //then
        // TODO adding a test for the creationDate
        response.statusCode(HttpStatus.CREATED.value())
                .body("id", notNullValue())
                .body("maximum", equalTo(100.0f))
                .body("minimum", equalTo(12.4f))
                .body("median", equalToObject(33.56f));
    }

    @Test
    @DisplayName("DELETE an CollectionValues by Id")
    public void givenCollectionValuesId_whenDELETEbyId_thenCollectionValuesIsDeleted() {
        //given
        Long authorId = 3L;

        //when
        ValidatableResponse response = given()
                .contentType("application/json")

                .when()
                .delete(baseURL + "/collectionValues/" + authorId)

                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("PUT an CollectionValues by Id to update it")
    public void givenIdAndUpdatedCollectionValues_whenPUTUpdate_thenCollectionValuesIsUpdated() {
        //given
        Long authorId = 5L;
        JsonObject collectionValuesJson = TestDataProvider.getCollectionValueJson();

        //when
        ValidatableResponse response2 = given()
                .contentType("application/json")
                .body(collectionValuesJson.toString())

                .when()
                .post(baseURL + "/collectionValues/")

                .prettyPeek()
                .then();

        //when
        ValidatableResponse response = given()
                .contentType("application/json")
                .body(collectionValuesJson.toString())
                .when()
                .put(baseURL + "/collectionValues/" + authorId)
                .prettyPeek()
                .then();

        response.statusCode(HttpStatus.OK.value());
    }
}
