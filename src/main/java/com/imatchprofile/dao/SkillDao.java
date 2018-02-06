/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.util.HibernateUtil;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author AmraniDriss
 */
public class SkillDao {
    
     public Skill addSkill(Skill s){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t =null;
        Skill findskill;
        try {
         CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Skill> query = session.getCriteriaBuilder().createQuery(Skill.class);
        Root<Skill> root = query.from(Skill.class);
        query.select(root).where(cb.like(root.<String>get("title"), s.getTitle()));
        findskill=session.createQuery(query).getSingleResult();
        session.clear();
        } catch (NoResultException ex) {
            
          findskill=null;
        }
        
        if(findskill==null){
            t = session.beginTransaction();
            session.save(s);
            findskill=s;
            t.commit();
        }
         System.out.println("com.imatchprofile.dao.SkillDao.addSkill()");
        //fermeture session
        session.close();
        return findskill;

    }
}
