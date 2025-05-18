package com.testingacademy.asserts;

import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.assertj.core.api.Assertions.*;

// weare creating resusable functions
// instaed of direct assertion we use functions

public class AssertActions {
    public void verifyRepsonseBody(String actual, String expected, String description){ // tis function can be called multiple times to verify assertions
        assertEquals(actual,expected,description);
    }

    public void verifyRepsonseBody(int actual, int expected, String description){
        assertEquals(actual,expected,description);
    }


    public void verifyStatusCode(Response response, Integer expected) {
        assertEquals(response.getStatusCode(),expected);
    }

    public void verifyStringKey(String keyExpect,String keyActual){
        // AssertJ
        assertThat(keyExpect).isNotNull();
        assertThat(keyExpect).isNotBlank();
        assertThat(keyExpect).isEqualTo(keyActual);

    }

    public void verifyStringKeyNotNull(Integer keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }
    public void verifyStringKeyNotNull(String keyExpect){
        // AssertJ
        assertThat(keyExpect).isNotNull();
    }

}
