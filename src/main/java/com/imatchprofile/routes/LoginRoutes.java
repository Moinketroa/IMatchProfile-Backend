/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.service.LoginService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author MasterChief
 */
@Path("login")
public class LoginRoutes {

    @Context
    private UriInfo context;
    
    private final LoginService loginService = new LoginService();

    /**
     * Retrieves representation of an instance of com.imatchprofile.routes.LoginRoutes
     * @return an instance of javax.ws.rs.core.Response
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJson(String content) {
        try {
            return Response.status(Response.Status.OK).entity(loginService.login(content)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        }
    }
}
