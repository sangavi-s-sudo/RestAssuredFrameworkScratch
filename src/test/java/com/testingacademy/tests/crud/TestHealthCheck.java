package com.testingacademy.tests.crud;

import com.testingacademy.base.BaseTest;
import com.testingacademy.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.annotations.Test;

public class TestHealthCheck extends BaseTest {


    @Test
    @Description("TC#3 - verify health check")
    public void testGetHealthCheck(){
        requestSpecification.basePath(APIConstants.PING_URL);

        response = RestAssured.given(requestSpecification).when().get();

        validatableResponse = response.then().log().all();

        validatableResponse.statusCode(201);


        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Created"));

    }
}
