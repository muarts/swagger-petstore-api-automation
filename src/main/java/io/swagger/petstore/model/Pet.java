package io.swagger.petstore.model;

import io.swagger.petstore.model.status.PetStatus;

import java.util.List;

public class Pet {
    private Long id;
    private Category category;
    private String name;
    private List<String> photoUrls;
    private List<Tag> tags;
    private PetStatus status;
}
