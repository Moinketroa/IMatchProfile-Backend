/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.service.CandidateService;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author achyle
 */
@Path("profil")
public class ProfilRoutes {
    
    private CandidateService candidateService = new CandidateService();
    
    public ProfilRoutes() {
    }
    
    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProfil(@PathParam("id") String id){
         try {
            return Response.status(Response.Status.OK).entity(candidateService.getProfilById(id)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }
}
