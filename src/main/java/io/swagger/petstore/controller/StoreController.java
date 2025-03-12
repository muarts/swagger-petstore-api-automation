package io.swagger.petstore.controller;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class StoreController extends ControllerBase {

    public Response getOrderById(Long orderId, int statusCode) {
        return given()
                .spec(requestSpecification)
                .pathParam("orderId", orderId)
                .when()
                .get("/store/order/{orderId}")
                .then()
                .assertThat()
                .statusCode(statusCode)
                .extract()
                .response();
    }
}
