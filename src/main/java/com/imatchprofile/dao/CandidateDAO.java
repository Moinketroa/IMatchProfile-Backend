/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Role;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import java.util.Vector;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author MasterChief
 */
public class CandidateDAO {
    
    public void create(User user) {
        Transaction trns = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            //creating a user without any subclass
            trns = session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            //creating a candidate with the user created
            Candidate newUsers = new Candidate(user, "", "", "", (byte) 1);
            trns = session.beginTransaction();
            session.save(newUsers);
            session.getTransaction().commit();
            //updating the user with the candidate created
            user.setCandidate(newUsers);
            user.setRole(Role.CANDIDATE.toString());
                
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
    
    public List<Candidate> findAll(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Candidate> queryCandidate = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> root = queryCandidate.from(Candidate.class);
        queryCandidate.select(root);
        List<Candidate> res = session.createQuery(queryCandidate).getResultList();
        //session.close();
        return res;
    }
    
     public List<Candidate> getUserCandidat(String title){
       
           Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Candidate> queryCandidate = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> root = queryCandidate.from(Candidate.class);
        queryCandidate.select(root).where(session.getCriteriaBuilder().equal(root.get("title"), title));
        List<Candidate> res = session.createQuery(queryCandidate).getResultList();
         
         //session.close();
        return res;
         
  }
}
