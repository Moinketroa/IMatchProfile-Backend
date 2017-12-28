/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.google.gson.Gson;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Role;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author MasterChief
 */
public class CandidateDAO {

    public void create(User userCandidate) {
        //creating a user without any subclass
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(userCandidate);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        //creating a candidate with the user created
        Candidate newCandidate = new Candidate(userCandidate, "", "", "", (byte) 1);
        
        trns = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.save(newCandidate);
            session.getTransaction().commit();
        } catch (RuntimeException e) {
            if (trns != null) {
                trns.rollback();
            }
            e.printStackTrace();
        } finally {
            session.close();
        }
        
        //updating the user with the candidate created
        userCandidate.setCandidate(newCandidate);
        userCandidate.setRole(Role.CANDIDATE.toString());
        trns = null;
        session = HibernateUtil.getSessionFactory().openSession();
        try {
            trns = session.beginTransaction();
            session.update(userCandidate);
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
