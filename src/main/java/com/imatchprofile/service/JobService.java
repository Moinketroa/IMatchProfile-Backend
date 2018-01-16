/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;
import javax.ws.rs.core.Response;
/**
 *
 * @author j-m_d
 */
public class JobService {
    
    private final JobDAO jobDAO = new JobDAO();
    
    public String getAllJob(){
        
        List<Job> listJobs = jobDAO.findAllJob();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < listJobs.size()-1;i++)
            sb.append(listJobs.get(i).allJson() + ",\n");
        sb.append(listJobs.get(listJobs.size()-1).allJson());
        sb.append("\n]");

        return sb.toString();
    }
    
    public String getJobById(String Id) throws IMPException{

        if(!isInteger(Id) || Id == null){
            throw new IMPPayloadException();
        }
        Job job = jobDAO.findOneById(Integer.parseInt(Id));
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return job.allJson();
    }
    
    public boolean isInteger(String s) {
    try { 
        Integer.parseInt(s); 
    } catch(NumberFormatException e) { 
        return false; 
    } catch(NullPointerException e) {
        return false;
    }
    // only got here if we didn't return false
    return true;
}
    
    
}
