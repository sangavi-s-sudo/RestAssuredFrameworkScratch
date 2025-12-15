package com.testingacademy.tests.sample;

import com.google.gson.JsonObject;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.requestSpecification;
import static org.hamcrest.Matchers.equalTo;

public class JsonPathFinder {
    @Test
    public void JsonResponse(){
        //APPROACH 1
//        given()
//                .when()
//                .get("http://localhost:3000/store")
//                .then()
//                .statusCode(200)
//                .header("Content-Type","aplication/json")
//                .body("book[3].title",equalTo("The Lord of the Rings"));
// APPROACH 2 --> MORE EFIICIENT IF JSON IS COMPLEX
        Response res = given().when().get("http://localhost:3000/store");
        Assert.assertEquals(res.getStatusCode(),200);
        Assert.assertEquals(res.header("Content-Type"),"application/json");
        String bookName =res.jsonPath().get("book[3].title").toString();
        Assert.assertEquals(bookName,"The Lord of the Rings");

        // APPROACH 3 -> IF THE JSON OBJECT IS DYNAMIC

        JSONObject jo = new JSONObject(res.asString());

        for(int i = 0; i<jo.getJSONArray("book").length(); i++) {
            String titleBook = jo.getJSONArray("book").getJSONObject(i).get("title").toString();

            System.out.println(titleBook);
        }

        //validate total price of books

        double totakPrice = 0;
        for(int i = 0; i<jo.getJSONArray("book").length(); i++) {
                String price = jo.getJSONArray("book").getJSONObject(i).get("price").toString();
            totakPrice = totakPrice + Double.parseDouble(price);
        }
    }



}
