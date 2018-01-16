/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.util.HibernateUtil;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 *
 * @author achyle
 */
@Path("profil")
public class ProfilRoutes {
    
    public ProfilRoutes() {
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Candidate> query = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);
        query.select(root);
        Candidate candidate = (Candidate) session.createQuery(query).getSingleResult();
        session.close();
        
        StringBuilder sb = new StringBuilder();
        sb.append(candidate.getCandidateId() + "\n");
        
        return "OK \n" ;//+ sb.toString();
    }
}
