/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.routes;

import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import org.hibernate.Session;

/**
 * REST Web Service
 *
 * @author MasterChief
 */
@Path("users")
public class UserRoutes {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of UserRoutes
     */
    public UserRoutes() {
    }

    /**
     * Retrieves representation of an instance of com.imatchprofile.routes.UserRoutes
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getJson() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        List<User> listUser = session.createQuery(query).getResultList();
        session.close();
        
        StringBuilder sb = new StringBuilder();
        for (User u : listUser)
            sb.append(u.getUserId() + "\n");
        
        return "OK \n" + sb.toString();
    }
}
