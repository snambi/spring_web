package com.example.demo.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public class UserResource {

    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }
}
