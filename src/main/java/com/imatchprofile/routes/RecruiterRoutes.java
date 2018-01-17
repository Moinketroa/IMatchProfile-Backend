/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.service.RecruiterService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author achyle
 */
@Path("recruiters")
public class RecruiterRoutes {
    
    private final RecruiterService recruiterService = new RecruiterService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postRecruiter(String content){
        try {
            return Response.status(Response.Status.CREATED).entity(recruiterService.signIn(content)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllRecruiter() {
        return Response.status(Response.Status.OK).entity(recruiterService.getAll()).build();
    }
}
