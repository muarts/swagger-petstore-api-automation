package io.swagger.petstore.testdata;

import io.swagger.petstore.model.status.PetStatus;
import org.testng.annotations.DataProvider;

public class DataProviders {

    @DataProvider(name = "statusDataProvider")
    public Object[][] statusDataProvider() {
        return new Object [][] {{PetStatus.available}, {PetStatus.pending}, {PetStatus.sold}};
    }

}
