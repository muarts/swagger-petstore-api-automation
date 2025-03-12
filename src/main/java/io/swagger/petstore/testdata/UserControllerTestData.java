package io.swagger.petstore.testdata;

import io.swagger.petstore.model.User;

import static io.swagger.petstore.testdata.Helper.getRandomString;

public class UserControllerTestData {

    public static User getUser() {
        return User.builder()
                .username(getRandomString())
                .firstName(getRandomString())
                .lastName(getRandomString())
                .email(getRandomString())
                .password(getRandomString())
                .phone(getRandomString())
                .build();
    }
}
