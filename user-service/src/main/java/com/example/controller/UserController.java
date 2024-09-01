package com.example.controller;

import com.example.entity.User;
import com.example.service.UserService;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.opentracing.Traced;

import java.util.List;

@Slf4j
@Path("/users")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "User Management", description = "User Management API")
@Traced
public class UserController {

    @Inject
    UserService userService;

    @POST
    @RolesAllowed("ADMIN")
    @Operation(summary = "Create new user", description = "Creates a new user in the system")
    public Response createUser(@Valid User user) {
        return Response.status(Response.Status.CREATED)
                .entity(userService.createUser(user)).build();
    }

    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    @Operation(summary = "Retrieve a user by ID", description = "Fetches a new user profile based on their ID.")
    public Response getUser(@PathParam("id") Long id) {
        return userService.getUserById(id)
                .map(user -> Response.ok(user).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @PUT
    @Path("/{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Update existing user", description = "Updates an existing user profile")
    public Response updateUser(@PathParam("id") Long id, @Valid User user) {
        return userService.updateUser(id, user)
                .map(updatedUser -> Response.ok(updatedUser).build())
                .orElse(Response.status(Response.Status.NOT_FOUND).build());
    }

    @DELETE
    @Path("{id}")
    @RolesAllowed("ADMIN")
    @Operation(summary = "Delete a user", description = "Deletes a user profile from the system")
    public Response deleteUser(@PathParam("id") Long id) {
        if (userService.deleteUser(id)) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @RolesAllowed("ADMIN")
    @Operation(summary = "List all users", description = "Lists all users with pagination.")
    public Response listUser() {
        List<User> users = userService.listAllUsers();
        return Response.ok(users).build();
    }
}
