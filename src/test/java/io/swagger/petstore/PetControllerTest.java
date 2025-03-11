package io.swagger.petstore;

import io.swagger.petstore.controller.PetController;
import io.swagger.petstore.model.Pet;
import io.swagger.petstore.model.status.PetStatus;
import io.swagger.petstore.testdata.DataProviders;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.List;

import static io.swagger.petstore.testdata.Helper.waitFor;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.either;
import static org.hamcrest.Matchers.equalTo;
import static io.swagger.petstore.testdata.PetControllerTestData.getPet;

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
        waitFor(3000);
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
        List<Pet> pets = petController.getPetsByStatus("available,pending", HttpStatus.SC_OK)
                .jsonPath().getList(".", Pet.class);

        pets.forEach(pet -> assertThat(pet.getStatus(), either(equalTo(PetStatus.available)).or(equalTo(PetStatus.pending))));
    }

    @Test
    public void testUpdateAnExistingPet() {
        Pet pet = getPet();
        petController.postPet(pet, HttpStatus.SC_OK).as(Pet.class);
        pet.setStatus(PetStatus.sold);
        pet.setName("Bella");
        Pet updatedPet = petController.updateAnExistingPet(pet, HttpStatus.SC_OK).as(Pet.class);

        assertThat(updatedPet.getStatus(), equalTo(pet.getStatus()));
        assertThat(updatedPet.getName(), equalTo("Bella"));
    }
}
