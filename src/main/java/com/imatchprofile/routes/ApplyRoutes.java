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
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMedia(@HeaderParam("Authorization") String token,
                                @PathParam("id") String idJob) {
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

}
