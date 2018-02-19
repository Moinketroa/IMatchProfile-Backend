/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.ApplyService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author j-m_d
 */
@Path("applies")
public class ApplyRoutes {
    
    private final ApplyService applyService = new ApplyService();

    @POST
    @Path("/{idJob}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response postApply(@HeaderParam("Authorization") String token,
                                @PathParam("idJob") String idJob) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = applyService.applyToJob(idJob, thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "apply", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }

    
    @DELETE
    @Path("/{idJob}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteApply(@HeaderParam("Authorization") String token,
                                @PathParam("idJob") String idJob) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            applyService.removeApply(idJob, thr.getUserId());
            return Response.status(Response.Status.OK).entity("{\"token\": \"" + thr.getNewToken() + "\"}").build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("/candidate/{pagenumber}/{entitiesperpage}/{idJob}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getApplyCandidate(@HeaderParam("Authorization") String token,
                                @PathParam("pagenumber") String pagenumber,
                                @PathParam("entitiesperpage") String entitiesPerPage,
                                @PathParam("idJob") String idJob) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = applyService.getApplyCandidate(idJob,pagenumber,entitiesPerPage, thr.getUserId());
              
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "apply", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
}
