package io.swagger.petstore.testdata;

import io.swagger.petstore.model.Category;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.Tag;
import io.swagger.petstore.model.status.PetStatus;

import java.util.List;

import static io.swagger.petstore.testdata.Helper.getRandomNumber;
import static io.swagger.petstore.testdata.Helper.getRandomString;

public class PetControllerTestData {

    public static Pet getPet() {
        Category category = Category.builder()
                .id(getRandomNumber())
                .name(getRandomString())
                .build();
        Tag tag = Tag.builder()
                .id(getRandomNumber())
                .name(getRandomString())
                .build();
        return Pet.builder()
                .id(getRandomNumber())
                .category(category)
                .name(getRandomString())
                .photoUrls(List.of(getRandomString()))
                .tags(List.of(tag))
                .status(PetStatus.available)
                .build();
    }
}
