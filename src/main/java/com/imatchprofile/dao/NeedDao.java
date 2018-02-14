/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Masters;
import com.imatchprofile.model.pojo.Needs;
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
public class NeedDao {
    
     public Needs Search(int job_id,int skill_id){
          Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t =null;
        Needs n=null;
        try {
         CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Needs> query = session.getCriteriaBuilder().createQuery(Needs.class);
        Root<Needs> root = query.from(Needs.class);
        query.select(root).where(cb.equal(root.<Integer>get("job").get("jobId"),job_id),
               cb.equal(root.<Integer>get("skill").get("skillId"),skill_id) ) ;
        n=session.createQuery(query).getSingleResult();
        session.clear();
        } catch (NoResultException ex) {
            
          n=null;
        }
        
        return n;
    }
    public Skill AddSkillToJob(int idjob,String s){
         JobDAO c = new JobDAO();
         Job job= c.findOneById(idjob);
         Skill skill =null;
         SkillDao skillDao = new SkillDao();
        if(job!=null){
            
            skill = skillDao.addSkill(s);
            MastersDao mastersDao = new MastersDao();
            if(Search(job.getJobId(), skill.getSkillId()) ==null){
            Needs n =new Needs(job, skill);
             
            job.getNeedses().add(n);
             Transaction transaction = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                session.merge(n);
                transaction.commit();
                 session.close();
                
            }
           return skill;
        }
         return null;
     } 
     
     public Job deleteSkill(int skill_id,int  job_id){
          JobDAO c = new JobDAO();
        Job job= c.findOneById(job_id);
         Needs needs=null;
         
        if(job!=null){
        needs= Search(job_id ,skill_id);
            
         if(needs!=null) {
              Transaction transaction = null;
                Session session = HibernateUtil.getSessionFactory().openSession();
                transaction = session.beginTransaction();
                Needs needtodelete = session.get(Needs.class, needs.getNeedsId());
                session.delete(needtodelete);
                transaction.commit();
                System.out.println("Master!=null");
                 session.close();
         }
         else job=null;
     }
        return job;
     }
}
