/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author j-m_d
 */
public class JobDAO {
    
    public List<Job> getMostRecent(int pageNumber, int entitiesPerPage) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
         /*
        try {
            transaction = session.beginTransaction();
        }*/
         
        return null;
    }
    
}
