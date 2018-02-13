/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import javax.persistence.NoResultException;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author j-m_d
 */
public class MediaDAO {
    
    public void create(Media newMedia) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        transaction = session.beginTransaction();
        session.save(newMedia);
        session.getTransaction().commit();
        session.close();
    }
    
    public Media findById(Integer id) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Media mediaFound;
        try {
            mediaFound = session.get(Media.class, id);
        } catch (NoResultException ex) {
            mediaFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return mediaFound;
    }

    public void delete(Media myMedia) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        //suppression du media
        session.remove(myMedia);
        
        transaction.commit();
        session.close();
    }
    
}
