/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.MasterService;
import com.imatchprofile.service.MatchingService;
import com.imatchprofile.service.NeedService;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author AmraniDriss
 */
@Path("matching")
public class MatchingRoutes {

    
    private final MatchingService matchingService = new MatchingService();
    
    
    @GET
    
    @Path("/{job_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchingetcandidate(@HeaderParam("Authorization") String token,@PathParam("job_id") String job_id){
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = matchingService.matchingcandidatejob(job_id, thr.getUserId());
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
      @GET
    
    @Produces(MediaType.APPLICATION_JSON)
    public Response matchingetcandidate(@HeaderParam("Authorization") String token){
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = matchingService.matchingjobcandidate(thr.getUserId());
            return Response.status(Response.Status.OK).entity(result).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    
  
}
