package com.testingacademy.tests.crud;

import com.testingacademy.base.BaseTest;
import com.testingacademy.endpoints.APIConstants;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.requestSpecification;

public class TestCreateToken extends BaseTest {

    @Test(groups = "reg", priority = 1)
    @Owner("Sangavi")
    @Description("TC#1 - Create Token and Verify")
    public void testAuth(){
        // Prep of Req
        requestSpecification.basePath(APIConstants.AUTH_URL);

        // Making of the Request.c
        response = RestAssured.given(requestSpecification).when().body(payloadManager.authPayload()).post();

        // Extraction ( JSON String response to Java Object
        String token = payloadManager.tokenResponseJava(response.asString());

        System.out.println(token);


        // Validation of the request.
        assertActions.verifyStringKeyNotNull(token);
    }
}
