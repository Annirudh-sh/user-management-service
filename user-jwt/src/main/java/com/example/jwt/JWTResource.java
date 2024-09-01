package com.example.jwt;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/jwt")
@ApplicationScoped
public class JWTResource {

    @Inject
    JWTService service;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getToken() {
        String token = service.generateUniversalToken();
        return Response.ok(token).build();
    }

    @GET
    @Path("/admin")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getAdminToken() {
        String token = service.generateAdminToken();
        return Response.ok(token).build();
    }

    @GET
    @Path("/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getUserToken() {
        String token = service.generateUserToken();
        return Response.ok(token).build();
    }
}
