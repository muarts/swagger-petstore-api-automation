package io.swagger.petstore;

import io.swagger.petstore.controller.UserController;
import io.swagger.petstore.model.ApiResponse;
import io.swagger.petstore.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;

import java.util.Date;

import static io.swagger.petstore.CommonConstants.*;
import static io.swagger.petstore.testdata.Helper.getRandomString;
import static io.swagger.petstore.testdata.Helper.getTime;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import static io.swagger.petstore.testdata.UserControllerTestData.getUser;

public class UserControllerTest {

    private final UserController userController = new UserController();

    @Test
    public void testCreateUser() {
        User user = getUser();
        ApiResponse apiResponse = userController.postUser(user, HttpStatus.SC_OK)
                .as(ApiResponse.class);

        assertThat(apiResponse.getCode(), equalTo(SUCCESS_CODE));
        assertThat(apiResponse.getType(), equalTo(API_RESPONSE_UNKNOWN_TYPE));
        assertThat(apiResponse.getMessage(), is(notNullValue()));
    }

    @Test
    public void testLogsUserIntoTheSystem() {
        ApiResponse apiResponse = userController.getLogin(getRandomString(), getRandomString(), HttpStatus.SC_OK)
                .as(ApiResponse.class);

        long loggedInTime = Long.parseLong(StringUtils.substringAfterLast(apiResponse.getMessage(), ":"));

        assertThat(apiResponse.getCode(), equalTo(SUCCESS_CODE));
        assertThat(apiResponse.getType(), equalTo(API_RESPONSE_UNKNOWN_TYPE));
        assertThat(apiResponse.getMessage(), containsString(LOGGED_IN_MESSAGE));
        assertThat(getTime() - loggedInTime, is(lessThanOrEqualTo(TOLERANCE_MILLIS)));
    }
}
