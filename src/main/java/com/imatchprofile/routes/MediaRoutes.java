/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import com.imatchprofile.service.MediaService;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
@Path("medias")
public class MediaRoutes {

    private MediaService mediaService = new MediaService();
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postMedia(  @HeaderParam("Authorization") String token,
                                String content) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            String result = mediaService.addMedia(content, thr.getUserId());
            return Response.status(Response.Status.CREATED).entity(TokenHelper.concatJsonsToken(result, "medias", thr.getNewToken())).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteMedia(@HeaderParam("Authorization") String token,
                                @PathParam("id") String idMedia) {
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            mediaService.removeMedia(idMedia, thr.getUserId()); 
            return Response.status(Response.Status.OK).entity("{\"token\": \"" + thr.getNewToken() + "\"}").build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{\"error\": \"" + ex.getErrorMessage() + "\"}").build();
        } catch (Throwable t) {
            t.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{\"error\": \"" + t.getMessage() + "\"}").build();
        }
    }
}
