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
import java.util.List;
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
public class MatchingDao {
    
    public List<Candidate>  MatchingCandidateJob(int job_id){
          Session session = HibernateUtil.getSessionFactory().openSession();
         CriteriaBuilder cb = session.getCriteriaBuilder();
         CriteriaQuery<Candidate> query = session.getCriteriaBuilder().createQuery(Candidate.class);
         Root<Candidate> candidate = query.from(Candidate.class);
          Root<Masters> masters = query.from(Masters.class);
          Root<Needs> needs = query.from(Needs.class);
          query.select(candidate).where(cb.and(cb.equal(needs.<Integer>get("job").get("jobId"),job_id),
                  (cb.equal(candidate.<Integer>get("candidateId"),masters.<Integer>get("candidate").get("candidateId"))),
                  (cb.equal(needs.<Integer>get("skill").get("skillId"), masters.<Integer>get("skill").get("skillId")))
                  ));
          query.groupBy(candidate.<Integer>get("candidateId"));
          query.orderBy(cb.desc(cb.count(masters)));
           List<Candidate> res = session.createQuery(query).getResultList();
       session.close();
        return res;
    }
     public List<Job>  MatchingJobCandidat(int candidate_id){
          Session session = HibernateUtil.getSessionFactory().openSession();
         CriteriaBuilder cb = session.getCriteriaBuilder();
         CriteriaQuery<Job> query = session.getCriteriaBuilder().createQuery(Job.class);
           Root<Job> job = query.from(Job.class);
         Root<Candidate> candidate = query.from(Candidate.class);
          Root<Masters> masters = query.from(Masters.class);
          Root<Needs> needs = query.from(Needs.class);
         query.select(job).where(cb.and((cb.equal(candidate.<Integer>get("candidateId"), candidate_id)) ,
                  (cb.equal(candidate.<Integer>get("candidateId"),masters.<Integer>get("candidate").get("candidateId"))),
                  (cb.equal(needs.<Integer>get("skill").get("skillId"), masters.<Integer>get("skill").get("skillId"))),
                  (cb.equal(needs.<Integer>get("job").get("jobId"),job.<Integer>get("jobId")))
                  
                  ));
         
          query.groupBy(job.<Integer>get("jobId"));
          query.orderBy(cb.desc(cb.count(needs)));
           List<Job> res = session.createQuery(query).getResultList();
         
       session.close();
        return res;
    }
   
}
