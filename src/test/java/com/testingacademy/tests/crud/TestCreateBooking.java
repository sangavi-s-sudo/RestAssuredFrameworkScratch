package com.testingacademy.tests.crud;

import com.testingacademy.base.BaseTest;
import com.testingacademy.endpoints.APIConstants;
import com.testingacademy.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.*;

public class TestCreateBooking extends BaseTest {
    @Test(groups = "reg", priority = 1)
    @Owner("Sangavi")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).log().all().post();
        System.out.println(response.asString());

        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Sangavi");
    }


    @Test(groups = "reg", priority = 1)
    @Owner("Sangavi")
    @Description("TC#1 - Verify that the Booking can't be Created, when payload is null")
    public void testCreateBookingPOST_Negative() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body("{}").log().all().post();
        System.out.println(response.asString());


        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(400);
    }


    @Test(groups = "reg", priority = 1)
    @Owner("Sangavi")
    @Description("TC#1 - Verify that the Booking can be Created using faker library")
    public void testCreateBookingPOST_PositiveRandom() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsStringUsingFaker()).post();
        System.out.println(response.asString());

        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Sangavi");
    }


}