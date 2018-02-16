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
     public Training DeleteTraining(Candidate c,Training s){
          Session session = HibernateUtil.getSessionFactory().openSession();
             Transaction t = session.beginTransaction();
             Training training = Search(c.getCandidateId(), s.getTrainingId());
            if( training!=null){
                
             session.delete(s);
           
             t.commit();
         
            return s;
            }
            return null;
     }
       public Training editTraining(Candidate c,Training s){
        Session session = HibernateUtil.getSessionFactory().openSession();
             Transaction t = session.beginTransaction();
           
             session.merge(s);
           
             t.commit();
         
            return s;
    }
        public Training findById(Integer id) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        Training trainingFound;
        try {
            trainingFound = session.get(Training.class, id);
        } catch (NoResultException ex) {
            trainingFound = null;
        }
        transaction.commit();
        //fermeture session
        session.close();
        return trainingFound;
    }
}
