package io.swagger.petstore.controller;

import io.restassured.response.Response;
import io.swagger.petstore.model.Pet;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PetController extends ControllerBase {

    public Response postPet(Pet pet, int statusCode) {
        return given()
                .spec(requestSpecification)
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
                .spec(requestSpecification)
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
                .spec(requestSpecification)
                .queryParam("status", status)
                .when()
                .get("/pet/findByStatus")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response updateAnExistingPet(Pet pet, int statusCode) {
        return given()
                .spec(requestSpecification)
                .contentType("application/json")
                .body(pet)
                .when()
                .put("/pet")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response deleteAPet(String petId, int statusCode) {
        return given()
                .spec(requestSpecification)
                .header("api_key", "special-key")
                .pathParam("petId", petId)
                .when()
                .delete("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response uploadImage(Integer petId, File file, int statusCode) {
        return given()
                .spec(requestSpecification)
                .pathParam("petId", petId)
                .contentType("multipart/form-data")
                .multiPart("file", file, "image/jpeg")
                .when()
                .post("/pet/{petId}/uploadImage")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response updateAPetWithFormData(String name, String status, Integer petId, int statusCode) {
        return given()
                .spec(requestSpecification)
                .pathParam("petId", petId)
                .contentType("application/x-www-form-urlencoded")
                .formParam("name", name)
                .formParam("status", status)
                .when()
                .post("/pet/{petId}")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
