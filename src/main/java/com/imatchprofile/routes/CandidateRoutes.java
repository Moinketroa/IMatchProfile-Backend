/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.service.CandidateService;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }
}
