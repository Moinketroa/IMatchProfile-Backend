/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.model.pojo.User;
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
    
    @Context
    private HttpServletResponse response;
    
    private CandidateDAO candidateDAO;

    /**
     * Creates a new instance of CandidateRoutes
     */
    public CandidateRoutes() {
        this.candidateDAO = new CandidateDAO();
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response postCandidate(String content){
        Gson g = new Gson();
        User userCandidate = g.fromJson(content, User.class);
        candidateDAO.create(userCandidate);
        if (userCandidate.getUserId() == null)
            return Response.status(Response.Status.BAD_REQUEST).entity(content).build();
        else
            return Response.status(Response.Status.CREATED).entity(userCandidate.toJSON()).build();
    }
}
