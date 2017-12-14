/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.imatchprofile.hibernatestuff;

import java.util.List;
import org.hibernate.Session;
import com.imatchprofile.imatchprofile.model.pojo.*;
import java.util.Vector;
import org.hibernate.query.Query;
/**
 *
 * @author AmraniDriss
 */
public class UserService {
    Session session=null;
   List<User> userslist;
    public UserService() {
     this.session = HibernateUtil.getSessionFactory().getCurrentSession();
    userslist=new Vector<>();
    userslist=ok();
    }
    public final List<User> ok() {
     List<User> userlist1=new  Vector<>();
    try {
        org.hibernate.Transaction tx = session.beginTransaction();
        Query q = session.createQuery("from User" );
        userlist1 = (List<User>) q.list();
        session.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
   return userlist1;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public List<User> getUserslist() {
        return userslist;
    }

    public void setUserslist(List<User> userslist) {
        this.userslist = userslist;
    }

 
    
 
    
}

