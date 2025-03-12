package io.swagger.petstore.controller;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class ControllerBase {

    protected final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBaseUri("https://petstore.swagger.io/v2")
            .setAccept("application/json")
            .build();
}
