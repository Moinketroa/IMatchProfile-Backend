/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.service.JobService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author brice
 */
@Path("jobs")
public class JobsRoutes {
    
    @Context
    private UriInfo context;

    private JobService jobService = new JobService();
    /**
     * Creates a new instance of UserRoutes
     */
    public JobsRoutes() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJobs() {
        return Response.status(Response.Status.OK).entity(jobService.getAllJob()).build();
    }
    
    @GET
    @Path("{id}")
    public Response getJob(@PathParam("id") String id){
         try {
            return Response.status(Response.Status.OK).entity(jobService.getJobById(id)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
         
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postJob(String content) {
        try {
            return Response.status(Response.Status.CREATED).entity(jobService.postJob(content)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
      @GET
    @Path("/{pagenumber}/{entitiesperpage}")

    public Response getRecentJobs(@PathParam("pagenumber") String pagenumber,@PathParam("entitiesperpage") String entitiesPerPage){
         try {
            return Response.status(Response.Status.OK).entity(jobService.getRecentJobs(pagenumber, entitiesPerPage)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }
    
    
    
    
    
}
