/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import com.imatchprofile.util.JsonUtil;
import com.imatchprofile.service.JobService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import org.hibernate.Session;

/**
 *
 * @author brice
 */
@Path("jobs")
public class JobsRoutes {
    
    @Context
    private UriInfo context;

    private JobService jobService = new JobService();
    /**
     * Creates a new instance of UserRoutes
     */
    public JobsRoutes() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Job> query = session.getCriteriaBuilder().createQuery(Job.class);
        Root<Job> root = query.from(Job.class);
        query.select(root);
        List<Job> listJobs = session.createQuery(query).getResultList();
       
        
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < listJobs.size()-1;i++)
            sb.append(listJobs.get(i).allJson() + ",\n");
        sb.append(listJobs.get(listJobs.size()-1).allJson());
        sb.append("\n]");
         session.close();
        return sb.toString();
    }
    
    @GET
    @Path("{id}")
    public Response getJob(@PathParam("id") String id){
         try {
            return Response.status(Response.Status.OK).entity(jobService.getJobById(id)).build();
        } catch (IMPException ex) {
            return Response.status(ex.getStatus()).entity("{}").build();
        } catch (Throwable t) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("{}").build();
        }
    }
    
    
}
