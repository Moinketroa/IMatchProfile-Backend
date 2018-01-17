/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.dao;

import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import java.util.Vector;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 *
 * @author j-m_d
 */
public class JobDAO {
    
    public List<Job> getMostRecent(int pageNumber, int entitiesPerPage) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Job> query = session.getCriteriaBuilder().createQuery(Job.class);

        Root<Job> root = query.from(Job.class);
        query.select(root);
        query.orderBy( session.getCriteriaBuilder().asc(root.get("createDate")));
        List<Job> res = session.createQuery(query).getResultList();
        List<Job> res1 = new Vector<>();
       
        for(int i=(pageNumber*entitiesPerPage)-entitiesPerPage;i<(pageNumber*entitiesPerPage) ;i++){
            Job job = new Job(res.get(i).getRecruiter(),res.get(i).getTitle(), res.get(i).getDescription()  , res.get(i).getVisibility(), res.get(i).getCreateDate());
            job.setJobId(res.get(i).getJobId());
            res1.add(job);
        }
        //session.close();
        return res1;
    }
    
    public void create(Job newJob) {
        
        Transaction transaction = null;
        Session session = HibernateUtil.getSessionFactory().openSession();
        
        transaction = session.beginTransaction();
        session.save(newJob);
        session.getTransaction().commit();
    }
    
    public Job findOneById(Integer id){
        Session session = HibernateUtil.getSessionFactory().openSession();
        Job res = (Job) session.get(Job.class, id);
        session.close();
        return res;
    }
     
    public List<Job> findAllJob(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        CriteriaQuery<Job> query = session.getCriteriaBuilder().createQuery(Job.class);
        Root<Job> root = query.from(Job.class);
        query.select(root);
        
        List<Job> res = session.createQuery(query).getResultList();
        session.close();
        return res;
    }
    
    
    
    
    
}
