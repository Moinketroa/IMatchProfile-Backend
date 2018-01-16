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
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.service.CandidateService;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.Session;

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
    
    /**
     * Retrieves representation of an instance of com.imatchprofile.routes.UserRoutes
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Session sessionCandidate = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Candidate> queryCandidate = sessionCandidate.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> rootCandidate = queryCandidate.from(Candidate.class);
        queryCandidate.select(rootCandidate);
        List<Candidate> listCandidate = sessionCandidate.createQuery(queryCandidate).getResultList();
        sessionCandidate.close();
        
        Session sessionUser = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<User> queryUser = sessionUser.getCriteriaBuilder().createQuery(User.class);
        Root<User> rootUser = queryUser.from(User.class);
        queryUser.select(rootUser);
        List<User> listUser = sessionUser.createQuery(queryUser).getResultList();
        sessionUser.close();
        
        StringBuilder json = new StringBuilder();
        json.append("[");
        for (int i=0; i < listCandidate.size(); i++){
            Candidate c = listCandidate.get(i);
            json.append("\n{\n");
            User u = c.getUser();
            for(User user : listUser){
                if(u.getUserId() == user.getUserId()){
                    json.append("\"userId\": " + "\""+ user.getUserId() + "\","+ "\n");
                    json.append("\"lastname\": " + "\""+ user.getLastname() + "\","+ "\n");
                    json.append("\"firstname\": "  + "\""+ user.getFirstname() + "\","+ "\n");
                }
            }
            json.append("\"title\": " + "\""+ c.getTitle() + "\","+ "\n");
            json.append("\"company\": " + "\""+ c.getCompany() + "\""+ "\n");
            if((i+1) == listCandidate.size()){
                json.append("}");
            }else{
                json.append("},");
            }
        }
        json.append("\n]");
           
        return json.toString();
    }
    
}
