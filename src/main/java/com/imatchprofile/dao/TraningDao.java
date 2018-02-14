/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Masters;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.Training;
import com.imatchprofile.util.HibernateUtil;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author AmraniDriss
 */
public class TraningDao {
    
    public Training Search(int candidate_id,int training_id){
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t =null;
        Training m=null;
        try {
         CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Training> query = session.getCriteriaBuilder().createQuery(Training.class);
        Root<Training> root = query.from(Training.class);
        query.select(root).where(cb.equal(root.<Integer>get("candidate").get("candidateId"),candidate_id),
               cb.equal(root.<Integer>get("trainingId"),training_id) ) ;
        m=session.createQuery(query).getSingleResult();
        session.clear();
        } catch (NoResultException ex) {
            
          m=null;
        }
        
        return m;
    }
    
     public Training AddTraining(Candidate c,Training s){
         
             Session session = HibernateUtil.getSessionFactory().openSession();
             Transaction t = session.beginTransaction();
             c.getTrainings().add(s);
             
            session.save(s);
           
            t.commit();
         
            return s;
     }
}
