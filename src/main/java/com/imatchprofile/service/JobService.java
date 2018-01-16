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
import javax.ws.rs.core.Response;
/**
 *
 * @author j-m_d
 */
public class JobService {
    
    private final JobDAO jobDAO = new JobDAO();
    
    public String getJobById(String Id) throws IMPException{

        if(!isInteger(Id) || Id == null){
            throw new IMPPayloadException();
        }        
        return jobDAO.findOneById(Integer.parseInt(Id)).allJson();
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
