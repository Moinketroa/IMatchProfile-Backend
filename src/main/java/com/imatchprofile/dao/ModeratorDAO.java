/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Moderator;
import com.imatchprofile.model.pojo.Role;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author achyle
 */
public class ModeratorDAO {
    
    public void create(User user) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //creating a user without any subclass
            trns = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            //creating a moderator with the user created
            Moderator newUsers = new Moderator(user);
            trns = session.beginTransaction();
            session.save(newUsers);
            session.getTransaction().commit();
            //updating the user with the candidate created
            user.setModerator(newUsers);
            user.setRole(Role.MODERATOR.toString());
                
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
    
}
