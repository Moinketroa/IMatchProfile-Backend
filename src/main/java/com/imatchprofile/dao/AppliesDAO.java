/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Applies;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import java.util.Vector;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author j-m_d
 */
public class AppliesDAO {
    
    public List<Candidate> getCandidateByJobs(String jobId){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Candidate> query = session.getCriteriaBuilder().createQuery(Candidate.class);
        Root<Applies> root = query.from(Applies.class);
        Expression<Integer> literal = cb.literal((Integer)   Integer.parseInt(jobId ));
        query.select(root.<Candidate>get("candidate"));
        query.where(cb.and(cb.equal(root.<Integer>get("job").get("jobId"), literal), cb.notEqual(root.get("candidate").get("visibility"), 0)));
        List<Candidate> res = session.createQuery(query).getResultList();
        return res;
       
        
    }
    
    public void create(Applies newApplies) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        transaction = session.beginTransaction();
        session.save(newApplies);
        session.getTransaction().commit();
        session.close();
    }
    
    public void delete(Applies apply) {
        //ouverture session
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        transaction = session.beginTransaction();
        
        //suppression du media
        session.remove(apply);
        
        transaction.commit();
        session.close();
    }
    
}
