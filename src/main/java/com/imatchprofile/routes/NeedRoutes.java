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
import com.imatchprofile.service.NeedService;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("needs")
public class NeedRoutes {

    
    private final NeedService skillService = new NeedService();
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postneedadd(@HeaderParam("Authorization") String token,String content){
        try {
              TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
              String result = skillService.addSkill(content,thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "skill", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @DELETE
    @Path("{job_id}/{skill_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    
    @Produces(MediaType.APPLICATION_JSON)
    public Response postSkillDelete(@HeaderParam("Authorization") String token,@PathParam("job_id") String job_id,@PathParam("skill_id") String skill_id){
        try {
              TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
              String result = skillService.deleteSkill(job_id,skill_id,thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "job", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    
  
}
