/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Applies;
import com.imatchprofile.model.pojo.Media;
import com.imatchprofile.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author j-m_d
 */
public class AppliesDAO {
    
    public void create(Applies newApplies) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        transaction = session.beginTransaction();
        session.save(newApplies);
        session.getTransaction().commit();
        session.close();
    }
    
}
