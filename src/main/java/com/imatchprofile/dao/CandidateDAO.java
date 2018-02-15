/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Role;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import java.util.Vector;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Expression;
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
    
    public List<Candidate> findAll(int pageNumber, int entitiesPerPage) throws IMPException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Candidate> queryCandidate = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> root = queryCandidate.from(Candidate.class);
        queryCandidate.select(root).where(session.getCriteriaBuilder().notEqual(root.get("visibility"), 0));
        List<Candidate> res = session.createQuery(queryCandidate).getResultList();
        List<Candidate> res1 = new Vector<>();
       
        if ((pageNumber*entitiesPerPage)-entitiesPerPage > res.size())
            throw new IMPNoContentException();
        
        for(int i=(pageNumber*entitiesPerPage)-entitiesPerPage;i<(pageNumber*entitiesPerPage) ;i++){
            if (i < res.size())
                res1.add(res.get(i));
        }
        
        session.close();
        return res1;
    }
    
     public List<Candidate> getCandidatByTitle(String title, int pageNumber, int entitiesPerPage) throws IMPException {
       
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Candidate> query = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);
        Expression<String> literal = cb.literal((String) "%" + title + "%");
        query.select(root).where(cb.and(cb.like(root.<String>get("title"), literal), cb.notEqual(root.get("visibility"), 0)));
        List<Candidate> res = session.createQuery(query).getResultList();
         
        List<Candidate> res1 = new Vector<>();
       
        if ((pageNumber*entitiesPerPage)-entitiesPerPage > res.size())
            throw new IMPNoContentException();
        
        for(int i=(pageNumber*entitiesPerPage)-entitiesPerPage;i<(pageNumber*entitiesPerPage) ;i++){
            if (i < res.size())
                res1.add(res.get(i));
        }
        
        session.close();
        return res1;
    }
     
    public Candidate findCandidateById(int candidateId) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Candidate candFound;
        try {
            candFound = session.get(Candidate.class, candidateId);
        } catch (NoResultException ex) {
            candFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return candFound;
    }
    
    public void editCandidate(Candidate editCandidate){
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        session.merge(editCandidate);
        session.getTransaction().commit();
    }
   
}
