/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPNotFoundEntityException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

/**
 *
 * @author j-m_d
 */
public class JobService extends Service {
    
    private JobDAO jobDAO = new JobDAO();
    private UserDAO userDAO = new UserDAO();
    
    public String postJob(String content, Integer userId) throws IMPException {
       //analyse du payload
        JSONObject payload = new JSONObject(content);
        String title, description;
        try {
            title = payload.getString("title");
            description = payload.getString("description");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(title, description)) {
            throw new IMPPayloadException();
        }
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
        Recruiter recruiter = userFound.getRecruiter();
        //verification si le user est un recruiter
        if (recruiter == null)
            throw new IMPNotARecruiterException();
        //creation de l'annonce
        Job newJob = new Job(recruiter, title, description, (byte) 1, new Date());
        jobDAO.create(newJob);
        return newJob.toJson().toString();
    }
    
    public String editJob(String content, String id, Integer userId) throws IMPException {
        
        //verification du parametre
        if(!isInteger(id) || id == null)
            throw new IMPWrongURLParameterException();
        
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        String title, description;
        try {
            title = payload.getString("title");
            description = payload.getString("description");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(title, description)) {
            throw new IMPPayloadException();
        }
        //recuperation du user authentifié
        User userFound = userDAO.findById(userId);
        //verification si id trouvé
        if (userFound == null)
            throw new IMPNotAUserException();
       
        //verification si le user est un recruiter
        Recruiter recruiter = userFound.getRecruiter();
        if (recruiter == null)
            throw new IMPNotARecruiterException();
        
        Job job = null;
        for(Job _job : recruiter.getJobs()){
            if(_job.getJobId() == Integer.parseInt(id)){
                _job.setTitle(title);
                _job.setDescription(description);
                job = _job;
            }
        }
        if (job != null) {
            jobDAO.editJob(job);
            return job.toJson().toString();
        } else {
            throw new IMPNotFoundEntityException("job");
        }
    }
    
    public String getAllJob(String pagenumber, String entitieperpages) throws IMPException {
        
        if(!isInteger(pagenumber) || pagenumber == null || !isInteger(entitieperpages) || entitieperpages == null){
            throw new IMPWrongURLParameterException();
        }
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        JSONArray listJobs = new JSONArray();
        
        for(Job job : jobDAO.findAllJob(pgNum, entPerPg)){
            if (job.getVisibility() != 0)
                listJobs.put(job.visiteurJsonObject());
        }
        
        return listJobs.toString();
    }
    
    public String getJobById(String Id) throws IMPException{

        if(!isInteger(Id) || Id == null)
            throw new IMPWrongURLParameterException();
        
        Job job = jobDAO.findOneById(Integer.parseInt(Id));
        
        if (job == null)
            throw new IMPNotFoundEntityException("job");
        
        return job.toJsonJob().toString();
    }
    
    public String getJobBytitle(String title, String pagenumber, String entitieperpages) throws IMPException {
        
        if(!isInteger(pagenumber) || !isInteger(entitieperpages))
            throw new IMPWrongURLParameterException();
        
        if(oneOfIsNull(title, pagenumber, entitieperpages))
            throw new IMPWrongURLParameterException();
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        JSONArray listJobs = new JSONArray();
        
        for(Job job : jobDAO.getJobbyTitle(title, pgNum, entPerPg)){
            if (job.getVisibility() != 0)
                listJobs.put(job.visiteurJsonObject());
        }
        
        return listJobs.toString();
    }

    public String getRecentJobs(String pagenumber, String entitieperpages) throws IMPException {

        if(!isInteger(pagenumber) || pagenumber == null || !isInteger(entitieperpages) || entitieperpages == null){
            throw new IMPWrongURLParameterException();
        }
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        JSONArray listJobs = new JSONArray();
        
        for (Job job : jobDAO.getMostRecent(pgNum, entPerPg)) {
            if (job.getVisibility() != 0)
                listJobs.put(job.visiteurJsonObject());
        }
        
        if (listJobs.length() == 0)
            throw new IMPNoContentException();
        
        return listJobs.toString();
    }

}
