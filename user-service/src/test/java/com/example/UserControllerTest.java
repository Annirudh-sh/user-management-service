package com.example;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
class UserControllerTest {
    @Test
    public void testCreateUser() {

        given()
                .header("Authorization", "Bearer " + "token")
                .contentType("application/json")
                .body("{\"username\":\"testUser\", \"email\":\"testuser@example.com\", \"password\":\"password\", \"role\":\"USER\"}")
                .when().post("/users")
                .then()
                .statusCode(201);
    }

}