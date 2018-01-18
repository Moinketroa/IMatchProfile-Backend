/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.JobService;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
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
    public Response getJobs(@HeaderParam("Authorization") String token) {
        try {
            TokenHelperResult thr = TokenHelper.verifyOptionalAndRefresh(token);
            String result = jobService.getAllJob();
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "jobs", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJob(@HeaderParam("Authorization") String token, @PathParam("id") String id){
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = jobService.getJobById(id);
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "job", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
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
    @Path("recent/{pagenumber}/{entitiesperpage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecentJobs(  @PathParam("pagenumber") String pagenumber,
                                    @PathParam("entitiesperpage") String entitiesPerPage,
                                    @HeaderParam("Authorization") String token) {
         try {
            TokenHelperResult thr = TokenHelper.verifyOptionalAndRefresh(token);
            String result = jobService.getRecentJobs(pagenumber, entitiesPerPage);
            return Response
                    .status(Response.Status.OK)
                    .entity(TokenHelper.concatJsonsToken(result, "jobs", thr.getNewToken()))
                    .build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }    
    
    @GET
    @Path("{title}")
    public Response getJobTitle(@PathParam("title") String title){
         try {
            return Response.status(Response.Status.OK).entity(jobService.getJobBytitle(title)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }    
}
