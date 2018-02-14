/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Experience;
import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.util.HibernateUtil;
import java.util.Date;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author brice
 */
public class ExperienceDAO {
    
    public Experience addExperience(Experience expenrience){
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t =null;
        t = session.beginTransaction();
        session.save(expenrience);
        t.commit();
        session.close();
        return expenrience;
    }
    
    public void editExperience(Experience editExperience){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.merge(editExperience);
        session.getTransaction().commit();
    }
    
    public void delete(Experience exp) {
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.remove(exp);
        transaction.commit();
        session.close();
    }
    
    public Experience findById(Integer id) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Experience experienceFound;
        try {
            experienceFound = session.get(Experience.class, id);
        } catch (NoResultException ex) {
            experienceFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return experienceFound;
    }
} 
    
