package com.testingacademy.modules;
// used to create payload


import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.testingacademy.pojos.request.Auth;
import com.testingacademy.pojos.request.Booking;
import com.testingacademy.pojos.request.BookingDates;
import com.testingacademy.pojos.response.BookingResponse;
import com.testingacademy.pojos.response.TokenResponse;

public class PayloadManager {

    // Convert the Java Object into the JSON String to use as Payload.
    // This is the example of Serialization


    // Serialization in Java is the process of converting an object into a byte stream.
// This allows the object to be saved to a file, sent over a network, or stored in a database.
// It is mainly used to save the state of an object or for sending objects between different Java Virtual Machines (JVMs) over a network.

    public String createPayloadBookingAsString() {
        Booking booking = new Booking();
        booking.setFirstname("Sangavi");
        booking.setLastname("S");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking); // this is in the form of java object


        Gson gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking; // this will get converted into JSON
    }


    // Convert the JSON String to Java Object so that we can verify response Body
    // DeSerialization
    //Deserialization in Java refers to the process of converting a byte stream (usually from a file or network) back into a Java object. It is the reverse operation of serialization, where an object is converted into a byte stream.
    public BookingResponse bookingResponseJava(String responseString) {// whaterver the response is coming as String format
        Gson gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class); // map responseString with BookingResponse class
// gson.fromJson is used to convert JSON to java object
        return bookingResponse;
    }



    // whatever the request we have to write both Serialization and DeSerialization in payloadManager file

    public String authPayload(){
        Auth auth = new Auth();
        auth.setPassword("password123");
        auth.setUsername("admin");

// Java object to JSON (Serialization)
        Gson gson = new Gson();
        String jsonAuth = gson.toJson(auth);
        return jsonAuth;
    }



// JSON to Java object  (DeSerialization)

    public String tokenResponseJava(String tokenResponse){

        Gson gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse,TokenResponse.class);
        return tokenResponse1.getToken();
    }


    public String createPayloadBookingAsStringUsingFaker() {
        Faker faker = new Faker();

        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(100));
        booking.setDepositpaid(faker.random().nextBoolean());

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking); // this is in the form of java object


        Gson gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking; // this will get converted into JSON
    }


    public Booking getBookingFromJson(String getResponse){

        Gson gson =  new Gson();
        Booking booking = gson.fromJson(getResponse,Booking.class);

        return booking;
    }


    public String fullPayloadUpdateAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Naveen");
        booking.setLastname("c");
        booking.setTotalprice(234);
        booking.setDepositpaid(false);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2025-02-01");
        bookingdates.setCheckout("2026-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Dinner");

        System.out.println(booking); // this is in the form of java object


        Gson gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking; // this will get converted into JSON

    }
}
