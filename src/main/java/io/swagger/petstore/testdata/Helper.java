package io.swagger.petstore.testdata;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Date;

public class Helper {

    public static Long getRandomNumber() {
        return Long.valueOf(RandomStringUtils.randomNumeric(5));
    }

    public static String getRandomString() {
        return RandomStringUtils.randomAlphanumeric(5);
    }

    public static long getTime() {
        Date date = new Date();
        return date.getTime();
    }
    
}
