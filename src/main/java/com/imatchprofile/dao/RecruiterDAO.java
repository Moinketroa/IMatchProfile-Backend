/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.Role;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author achyle
 */
public class RecruiterDAO {
    
    public void create(User user) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //creating a user without any subclass
            trns = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            //creating a recruiter with the user created
            Recruiter newUsers = new Recruiter(user, "", "");
            trns = session.beginTransaction();
            session.save(newUsers);
            session.getTransaction().commit();
            //updating the user with the candidate created
            user.setRecruiter(newUsers);
            user.setRole(Role.RECRUITER.toString());
                
            trns = session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
    
    public List<Recruiter> findAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Recruiter> queryRecruiter = session.getCriteriaBuilder().createQuery(Recruiter.class);
        Root<Recruiter> root = queryRecruiter.from(Recruiter.class);
        queryRecruiter.select(root);
        List<Recruiter> res = session.createQuery(queryRecruiter).getResultList();
        //session.close();
        return res;
    }
    
    public Recruiter findRecruiterById(int recruiterId) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Recruiter recruFound;
        try {
            recruFound = session.get(Recruiter.class, recruiterId);
        } catch (NoResultException ex) {
            recruFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return recruFound;
    }
    
    public void editRecruiter(Recruiter editRecruiter){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.merge(editRecruiter);
        session.getTransaction().commit();
        session.close();
    }
}
