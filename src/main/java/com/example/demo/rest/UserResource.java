package com.example.demo.rest;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path("/employees")
public class UserResource {

    // This method is called if TEXT_PLAIN is request
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayPlainTextHello() {
        return "Hello Jersey";
    }
}
