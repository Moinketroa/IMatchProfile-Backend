/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.helper.TokenHelperResult;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;

/**
 * REST Web Service
 *
 * @author j-m_d
 */
@Path("token")
public class TokenRoutes {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTokenStatus(@HeaderParam("Authorization") String token) {
        JSONObject result = new JSONObject();
        
        try {
            TokenHelperResult thr = TokenHelper.verifyNeededAndRefresh(token);
            result.put("token_status", "OK");
            result.put("token", thr.getNewToken());
        } catch (IMPException ex) {
            result.put("token_status", "Expired or not valid");
        }
        
        return Response.status(Response.Status.OK).entity(result.toString()).build();
    }
}
