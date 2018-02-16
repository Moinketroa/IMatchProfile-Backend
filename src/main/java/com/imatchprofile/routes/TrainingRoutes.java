/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.TrainingService;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author AmraniDriss
 */
@Path("trainings")
public class TrainingRoutes {
    
        private final TrainingService trainingService = new TrainingService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postaddtraining(@HeaderParam("Authorization") String token,String content){
        try {
            
              TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
             
              String result = trainingService.addTraining(content,thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "training", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
             t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @DELETE
    @Path("{training_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postFormationDelete(@HeaderParam("Authorization") String token,@PathParam("training_id") String training_id){
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = trainingService.deleteTraining(thr.getUserId(),training_id);
            return Response.status(Response.Status.OK).entity("{\"token\": \"" + thr.getNewToken() + "\"}").build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response pustupdatetraining(@HeaderParam("Authorization") String token,String content
    , @PathParam("id") String idtraining){
        try {
            
              TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
             
              String result = trainingService.updateTraining(thr.getUserId(),content,idtraining);
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "training", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
             t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
}
