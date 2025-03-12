package io.swagger.petstore.controller;

import io.restassured.response.Response;
import io.swagger.petstore.model.User;

import static io.restassured.RestAssured.given;

public class UserController {

    public Response postUser(User user, int statusCode) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .accept("application/json")
                .contentType("application/json")
                .body(user)
                .when()
                .post("/user")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }

    public Response getLogin(String username, String password, int statusCode) {
        return given()
                .baseUri("https://petstore.swagger.io/v2")
                .accept("application/json")
                .queryParam("username", username)
                .queryParam("password", password)
                .when()
                .get("/user/login")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
