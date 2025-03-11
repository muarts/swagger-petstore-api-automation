package io.swagger.petstore.controller;

import io.restassured.response.Response;
import io.swagger.petstore.model.Pet;

import static io.restassured.RestAssured.given;


public class PetController {

    public Response postPet(Pet pet, int statusCode) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .accept("application/json")
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response getPetByPetId(String petId, int statusCode) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .accept("application/json")
                .pathParam("petId", petId)
                .when()
                .get("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response getPetsByStatus(String status, int statusCode) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .accept("application/json")
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
