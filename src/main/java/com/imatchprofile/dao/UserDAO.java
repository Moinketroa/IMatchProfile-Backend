/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
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
    
    public User findById(Integer id) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        User userFound;
        try {
            userFound = session.get(User.class, id);
        } catch (NoResultException ex) {
            userFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return userFound;
    }
    
    public List<User> findAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<User> query = session.getCriteriaBuilder().createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root);
        List<User> res = session.createQuery(query).getResultList();
        //session.close();
        return res;
    }
    
    public void editUser(User editUser){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.merge(editUser);
        session.getTransaction().commit();
    }
   
    
}
