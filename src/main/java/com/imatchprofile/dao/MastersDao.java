/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Masters;
import com.imatchprofile.model.pojo.Skill;
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
public class MastersDao {
    
    public Masters Search(int candidate_id,int skill_id){
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t =null;
        Masters m=null;
        try {
         CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Masters> query = session.getCriteriaBuilder().createQuery(Masters.class);
        Root<Masters> root = query.from(Masters.class);
        query.select(root).where(cb.equal(root.<Integer>get("candidate").get("candidateId"),candidate_id),
               cb.equal(root.<Integer>get("skill").get("skillId"),skill_id) ) ;
        m=session.createQuery(query).getSingleResult();
        session.clear();
        } catch (NoResultException ex) {
            
          m=null;
        }
        
        return m;
    }
    
}
