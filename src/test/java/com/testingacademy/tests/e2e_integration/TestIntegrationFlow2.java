package com.testingacademy.tests.e2e_integration;

import com.testingacademy.base.BaseTest;
import com.testingacademy.endpoints.APIConstants;
import com.testingacademy.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow2 extends BaseTest {


//    1. Create Booking -> Delete it -> Verify



    @Test(groups = "reg", priority = 1)
    @Owner("Sangavi")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive(ITestContext iTestContext) {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).post();
        System.out.println(response.asString());

        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Sangavi");

        iTestContext.setAttribute("bookingid", bookingResponse.getBookingid());
    }




    @Test(groups = "qa", priority = 2)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 2. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token1", token);
        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        requestSpecification.basePath(basePathDelete);

        response = RestAssured.given(requestSpecification).cookie("token", token).when().delete();


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Created"));
    }


    @Test(groups = "qa", priority = 3)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 3. Verify that the Booking By deleted ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        requestSpecification.basePath(basePathGET);

        response = RestAssured.given(requestSpecification).when().get();


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(404);


        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Not Found"));

    }


    @Test(groups = "qa", priority = 4)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID after deletion")
    public void testUpdateBookingByID(ITestContext iTestContext) {

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token1", token);


        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullPayloadUpdateAsString()).put();


        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(405);

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Method Not Allowed"));
    }


    @Test(groups = "qa", priority = 3)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 3. Verify that the Booking By deleted ID")
    public void testVerifyAllBookingId(ITestContext iTestContext) {

        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL ;
        requestSpecification.basePath(basePathGET);

        response = RestAssured.given(requestSpecification).when().get();

        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);


    }


}
