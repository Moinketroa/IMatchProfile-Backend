/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 *
 * @author MasterChief
 */
public class UserDAO {
    
    public User findOneByEmail(String email){
        
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        //select * from user where email=email
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root).where(builder.equal(root.get("email"), email));
        Query<User> q = session.createQuery(query);
        User userFound;
        
        try {
            userFound = q.getSingleResult();
        } catch (NoResultException ex) {
            userFound = null;
        }
        
        transaction.commit();
        
        //fermeture session
        session.close();
        
        return userFound;
    }
    
}
