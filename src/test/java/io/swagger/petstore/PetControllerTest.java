package io.swagger.petstore;

import io.swagger.petstore.controller.PetController;
import io.swagger.petstore.model.ApiResponse;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.status.PetStatus;
import io.swagger.petstore.testdata.DataProviders;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.io.File;
import java.util.List;

import static io.swagger.petstore.CommonConstants.*;
import static io.swagger.petstore.ErrorMessageConstants.*;
import static io.swagger.petstore.testdata.Helper.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static io.swagger.petstore.testdata.PetControllerTestData.getPet;
import static org.hamcrest.Matchers.*;

public class PetControllerTest {

    private final PetController petController = new PetController();

    @Test
    public void testAddANewPetToTheStore() {
        Pet pet = getPet();
        Pet addedPet = petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);

        assertThat(addedPet.getId(), equalTo(pet.getId()));
        assertThat(addedPet.getCategory(), equalTo(pet.getCategory()));
        assertThat(addedPet.getName(), equalTo(pet.getName()));
        assertThat(addedPet.getPhotoUrls(), equalTo(pet.getPhotoUrls()));
        assertThat(addedPet.getTags(), equalTo(pet.getTags()));
        assertThat(addedPet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test
    public void testFindPetByID() {
        Pet pet = getPet();
        petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        Pet retrievedPet = petController.getPetByPetId(String.valueOf(pet.getId()), HttpStatus.SC_OK)
                .as(Pet.class);

        assertThat(retrievedPet.getId(), equalTo(pet.getId()));
        assertThat(retrievedPet.getCategory(), equalTo(pet.getCategory()));
        assertThat(retrievedPet.getName(), equalTo(pet.getName()));
        assertThat(retrievedPet.getPhotoUrls(), equalTo(pet.getPhotoUrls()));
        assertThat(retrievedPet.getTags(), equalTo(pet.getTags()));
        assertThat(retrievedPet.getStatus(), equalTo(pet.getStatus()));
    }

    @Test(dataProvider = "statusDataProvider", dataProviderClass = DataProviders.class)
    public void testFindPetsByTheirStatus(PetStatus petStatus) {
        List<Pet> pets = petController.getPetsByStatus(petStatus.toString(), HttpStatus.SC_OK)
                .jsonPath().getList(".", Pet.class);

        pets.forEach(pet -> assertThat(pet.getStatus(), equalTo(petStatus)));
    }

    @Test
    public void testFindPetsByMultipleStatusValues() {
        String statusesUnderSearch = String.format("%s,%s", PetStatus.available, PetStatus.pending);
        List<Pet> pets = petController.getPetsByStatus(statusesUnderSearch, HttpStatus.SC_OK)
                .jsonPath().getList(".", Pet.class);

        pets.forEach(pet -> {
            assertThat(pet.getStatus(), either(equalTo(PetStatus.available)).or(equalTo(PetStatus.pending)));
        });
    }

    @Test
    public void testUpdateAnExistingPet() {
        Pet pet = getPet();
        petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        pet.setStatus(PetStatus.sold);
        pet.setName(UPDATED_DOG_NAME);
        Pet updatedPet = petController.updateAnExistingPet(pet, HttpStatus.SC_OK).as(Pet.class);

        assertThat(updatedPet.getStatus(), equalTo(pet.getStatus()));
        assertThat(updatedPet.getName(), equalTo(UPDATED_DOG_NAME));
    }

    @Test
    public void testDeletesAPet() {
        Pet pet = getPet();
        petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        petController.deleteAPet(String.valueOf(pet.getId()), HttpStatus.SC_OK);
        ApiResponse apiResponse = petController.getPetByPetId(String.valueOf(pet.getId()), HttpStatus.SC_NOT_FOUND)
                .as(ApiResponse.class);

        assertThat(apiResponse.getCode(), equalTo(ERROR_CODE));
        assertThat(apiResponse.getType(), equalTo(ERROR_TYPE));
        assertThat(apiResponse.getMessage(), equalTo(PET_NOT_FOUND));
    }

    @Test
    public void testReturnAnErrorIfPetToDeleteDoesNotExist() {
        petController.deleteAPet(String.valueOf(getRandomNumber()), HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void testReturnAnErrorIfPetToRetrieveDoesNotExist() {
        ApiResponse apiResponse = petController.getPetByPetId(String.valueOf(getRandomNumber()), HttpStatus.SC_NOT_FOUND)
                .as(ApiResponse.class);

        assertThat(apiResponse.getCode(), equalTo(ERROR_CODE));
        assertThat(apiResponse.getType(), equalTo(ERROR_TYPE));
        assertThat(apiResponse.getMessage(), equalTo(PET_NOT_FOUND));
    }

    @Test
    public void testUploadAnImageOfPet() {
        Pet pet = getPet();
        Pet dog = petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        File file = new File(FILEPATH_TO_IMAGE);
        ApiResponse apiResponse = petController.uploadImage(Integer.valueOf(String.valueOf(dog.getId())), file, HttpStatus.SC_OK)
                .as(ApiResponse.class);

        assertThat(apiResponse.getCode(), equalTo(SUCCESS_CODE));
        assertThat(apiResponse.getType(), equalTo(API_RESPONSE_UNKNOWN_TYPE));
        assertThat(apiResponse.getMessage(), containsString(IMAGE_NAME));
    }

    @Test
    public void testUpdateAPetInTheStoreWithFormData() {
        Pet pet = getPet();
        Pet petToUpdate = petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        Integer petId = Integer.valueOf(String.valueOf(petToUpdate.getId()));
        String newName = getRandomString();
        ApiResponse apiResponse = petController.updateAPetWithFormData(newName, PetStatus.sold.toString(), petId, HttpStatus.SC_OK)
                .as(ApiResponse.class);

        assertThat(apiResponse.getCode(), equalTo(SUCCESS_CODE));
        assertThat(apiResponse.getType(), equalTo(API_RESPONSE_UNKNOWN_TYPE));
        assertThat(apiResponse.getMessage(), equalTo(String.valueOf(petId)));
    }
}
