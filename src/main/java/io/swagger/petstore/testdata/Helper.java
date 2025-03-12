package io.swagger.petstore.testdata;

import org.apache.commons.lang3.RandomStringUtils;

public class Helper {

    public static Long getRandomNumber() {
        return Long.valueOf(RandomStringUtils.randomNumeric(5));
    }

    public static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(5);
    }
    
}
