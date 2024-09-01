package com.example;

import com.example.entity.User;
import com.example.service.UserService;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;

@QuarkusTest
class UserControllerTest {

    @Inject
    UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = mock(UserService.class);
    }

    @Test
    public void testCreateUser() {
        User user = new User();
        user.setUsername("Test User");

        Mockito.when(userService.createUser(any(User.class))).thenReturn(user);

        given().contentType(ContentType.JSON)
                .body(user)
                .when().post("/users")
                .then().statusCode(201)
                .body("username", is("Test User"));
    }

    @Test
    public void testGetUser() {
        User user = new User();
        user.setId(1L);
        user.setUsername("Test User");

        Mockito.when(userService.getUserById(anyLong())).thenReturn(Optional.of(user));

        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("username", is("Test User"));
    }

    @Test
    public void testUpdateUser() {
        User updatedUser = new User();
        updatedUser.setId(1L);
        updatedUser.setUsername("Updated User");

        Mockito.when(userService.updateUser(anyLong(), any(User.class))).thenReturn(Optional.of(updatedUser));

        given()
                .contentType(ContentType.JSON)
                .body(updatedUser)
                .when()
                .put("/users/1")
                .then()
                .statusCode(200)
                .body("id", is(1))
                .body("username", is("Updated User"));
    }


    @Test
    public void testDeleteUser() {
        Mockito.when(userService.deleteUser(anyLong())).thenReturn(true);

        given()
                .when()
                .delete("/users/1")
                .then()
                .statusCode(204);
    }


    @Test
    public void testListUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setUsername("User 1");

        User user2 = new User();
        user2.setId(2L);
        user2.setUsername("User 2");

        Mockito.when(userService.listAllUsers()).thenReturn(List.of(user1, user2));

        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", is(2))
                .body("[0].id", is(1))
                .body("[0].name", is("User 1"))
                .body("[1].id", is(2))
                .body("[1].name", is("User 2"));
    }

}