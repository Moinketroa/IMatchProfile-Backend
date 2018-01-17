/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.service.UserService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author MasterChief
 */
@Path("users")
public class UserRoutes {

    @Context
    private UriInfo context;
    private UserService userService = new UserService();

    /**
     * Creates a new instance of UserRoutes
     */
    public UserRoutes() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsers() {
        return Response.status(Response.Status.OK).entity(userService.getAllUsers()).build();
    } 
    
}
