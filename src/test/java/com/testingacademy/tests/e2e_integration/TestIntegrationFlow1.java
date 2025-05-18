package com.testingacademy.tests.e2e_integration;

import com.testingacademy.base.BaseTest;
import com.testingacademy.endpoints.APIConstants;
import com.testingacademy.pojos.request.Booking;
import com.testingacademy.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.apache.commons.logging.LogFactory;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow1 extends BaseTest {

    // TestE2EFlow_01

    //  Test E2E Scenario 1

    //  1. Create a Booking -> bookingID

    // 2. Create Token -> token

    // 3. Verify that the Create Booking is working - GET Request to bookingID

    // 4. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request

    // 5. Delete the Booking - Need to get the token, bookingID from above request


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

        // bookingid can also be put in base test - but we are not doing it becoz we will be manipulating it .
        // but token can be baseTest file beccoz we are not manipulating it
    }

    @Test(groups = "qa", priority = 2)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        requestSpecification.basePath(basePathGET);

        response = RestAssured.given(requestSpecification).when().get();


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getBookingFromJson(response.asString());

        assertActions.verifyStringKeyNotNull(booking.getFirstname());
    }


    @Test(groups = "qa", priority = 3)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
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
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getBookingFromJson(response.asString());

        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(), "Naveen");
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Sangavi")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext) {
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token =(String) iTestContext.getAttribute("token1");
        String basePathDelete = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        requestSpecification.basePath(basePathDelete);

        response = RestAssured.given(requestSpecification).cookie("token", token).when().delete();


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(201);

        String responseBody = response.getBody().asString();
        Assert.assertTrue(responseBody.contains("Created"));
    }
}