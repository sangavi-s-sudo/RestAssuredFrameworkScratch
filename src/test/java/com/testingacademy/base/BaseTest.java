package com.testingacademy.base;

import com.testingacademy.asserts.AssertActions;
import com.testingacademy.endpoints.APIConstants;
import com.testingacademy.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    // CommonToAll Testcase
    //   // Base URL, Content Type - json - common
    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;

    @BeforeTest
    public void setup(){
        System.out.println("Starting the test!!");
         payloadManager = new PayloadManager();
         assertActions = new AssertActions();
// below three lines are common for all CRUD's where
//        requestSpecification = RestAssured.given();
//        requestSpecification.baseUri(APIConstants.BASE_URL);
//        requestSpecification.contentType(ContentType.JSON).log().all();


        // Above three lines can be written in one more way using
        //RequestSpecBuilder -> means before running your requests what is the important building logs required
        requestSpecification = new RequestSpecBuilder().setBaseUri(APIConstants.BASE_URL).addHeader("Content-Type", "application/json")
                .build().log().all();
    }

    public String getToken(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);

        String auth1Payload = payloadManager.authPayload();
        response = requestSpecification.contentType(ContentType.JSON).body(auth1Payload).when().post();



        String token = payloadManager.tokenResponseJava(response.asString());

        return token;
    }

    @AfterTest
    public void tearDown(){
        System.out.println("Test got finished!");
    }

}
