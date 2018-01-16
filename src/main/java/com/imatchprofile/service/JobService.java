/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.model.pojo.Job;
/**
 *
 * @author j-m_d
 */
public class JobService {
    
    private final JobDAO jobDAO = new JobDAO();
    
    public Job getJobById(String Id) throws IMPException{
        
        if()
        
        if(isInteger(Id))
        
        
        return null;
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
