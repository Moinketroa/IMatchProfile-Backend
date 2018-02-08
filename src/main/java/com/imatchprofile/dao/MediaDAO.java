/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.util.HibernateUtil;
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
    }
    
}
