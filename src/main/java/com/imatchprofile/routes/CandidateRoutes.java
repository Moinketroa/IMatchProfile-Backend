/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.CandidateService;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
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
 * @author MasterChief
 */
@Path("candidates")
public class CandidateRoutes {

    @Context
    private UriInfo context;
    
    private final CandidateService candidateService = new CandidateService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCandidate(String content){
        try {
            return Response.status(Response.Status.CREATED).entity(candidateService.signIn(content)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("{pagenumber}/{entitiesperpage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll( @PathParam("pagenumber") String pagenumber,
                            @PathParam("entitiesperpage") String entitiesPerPage,
                            @HeaderParam("Authorization") String token) {
        try {
            TokenHelperResult thr = TokenHelper.verifyOptionalAndRefresh(token);
            String result = candidateService.getAll(pagenumber, entitiesPerPage);   
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "candidates", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfil(  @HeaderParam("Authorization") String token,
                                @PathParam("id") String id){
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = candidateService.getProfilById(id);
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "candidate", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("/me")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyCandidate(@HeaderParam("Authorization") String token) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = candidateService.getMyProfile(thr.getUserId());
            return Response.status(Response.Status.OK).entity(TokenHelper.concatJsonsToken(result, "candidate", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @GET
    @Path("search/{title}/{pagenumber}/{entitiesperpage}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response searchCandidatByQuery(  @PathParam("title") String title,
                                            @PathParam("pagenumber") String pagenumber,
                                            @PathParam("entitiesperpage") String entitiesPerPage,
                                            @HeaderParam("Authorization") String token) {
        try {
            TokenHelperResult thr = TokenHelper.verifyOptionalAndRefresh(token);
            String result = candidateService.getcandidatesbytitle(title, pagenumber, entitiesPerPage);
            return Response
                    .status(Response.Status.OK)
                    .entity(TokenHelper.concatJsonsToken(result, "candidates", thr.getNewToken()))
                    .build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
}
