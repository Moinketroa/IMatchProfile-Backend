/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
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
    public Skill AddSkillToCandidat(int idcandidat,String s){
         CandidateDAO c = new CandidateDAO();
        Candidate candidate= c.findCandidateById(idcandidat);
         Skill skill =null;
         SkillDao skillDao = new SkillDao();
        if(candidate!=null){
            
            skill = skillDao.addSkill(s);
            MastersDao mastersDao = new MastersDao();
            if(mastersDao.Search(candidate.getCandidateId(), skill.getSkillId()) ==null){
            Masters m =new Masters(candidate, skill);
             
            candidate.getMasterses().add(m);
             Transaction transaction = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                session.merge(m);
                transaction.commit();
                 session.close();
                
            }
           return skill;
        }
         return null;
     } 
     
     public Candidate deleteSkill(int skill_id,int  candidate_id){
          CandidateDAO c = new CandidateDAO();
        Candidate candidate= c.findCandidateById(candidate_id);
         Masters masters=null;
         
        if(candidate!=null){
         MastersDao m = new MastersDao();
        masters= m.Search(candidate_id ,skill_id);
            
         if(masters!=null) {
              Transaction transaction = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Masters masterstodelete = session.get(Masters.class, masters.getMastersId());
                session.delete(masterstodelete);
                transaction.commit();
                System.out.println("Master!=null");
                 session.close();
         }
         else candidate=null;
     }
        return candidate;
     }
    
}
