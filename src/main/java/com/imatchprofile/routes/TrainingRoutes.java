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
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
             System.out.println("com.imatchprofile.routes.TrainingRoutes.postaddtraining()");
            
              TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
             
              String result = trainingService.addTraining(content,thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "training", thr.getNewToken())).build();
        } catch (IMPException ex) {
              System.out.println("com.imatchprofile.routes.TrainingRoutes.postaddtraining()2");
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
             t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
}
