/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPExpiredTokenException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.exceptions.IMPNoTokenException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongTokenException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;
import com.imatchprofile.util.HibernateUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author j-m_d
 */
public class JobService extends Service {
    
    private JobDAO jobDAO = new JobDAO();
    private UserDAO userDAO = new UserDAO();
    
    public String postJob(String content) throws IMPException {
        
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        
        String title, description, token;
        
        try {
            title = payload.getString("title");
            description = payload.getString("description");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        
        //verification présence token
        try {
            token = payload.getString("token");
        } catch (JSONException e) {
            throw new IMPNoTokenException();
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(title, description)) {
            throw new IMPPayloadException();
        }
        if (token == null) {
            throw new IMPNoTokenException();
        }
        
        //traitement du token
        Integer userId;
        try {
            userId = JWTHelper.decrypt(token);
        } catch (IllegalArgumentException | UnsupportedEncodingException | JWTDecodeException ex) {
            throw new IMPWrongTokenException();
        }
        if (userId == null) {
            throw new IMPExpiredTokenException();
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
        
        //regeneration du token
        String newToken;
        
        try {
            newToken = JWTHelper.createToken(userId);
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new IMPInternalServerException(ex.getMessage());
        }
        
        JSONObject response = new JSONObject();
        response.put("token", newToken);
        response.put("job", newJob.toJson());
        
        return response.toString();
    }
    
    public String getAllJob(){
        JSONArray listJobs = new JSONArray();
        
        for(Job job : jobDAO.findAllJob()){
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
            throw new IMPNoContentException();
        
        return job.toJsonJob().toString();
    }
      public String getJobBytitle(String title) throws IMPNoContentException {

       
         JSONArray listJobs = new JSONArray();
        
        for(Job job : jobDAO.getJobbyTitle(title)){
            listJobs.put(job.toJson());
        }
        
        return listJobs.toString();
    }

    public String getRecentJobs(String pagenumber,String entitieperpages) throws IMPException{

        if(!isInteger(pagenumber) || pagenumber == null || !isInteger(entitieperpages) || entitieperpages == null){
            throw new IMPWrongURLParameterException();
        }
        
        JSONArray listJobs = new JSONArray();
        
        for (Job job : jobDAO.getMostRecent(Integer.parseInt(pagenumber),Integer.parseInt(entitieperpages))) {
            if (job.getVisibility() != 0)
                listJobs.put(job.visiteurJsonObject());
        }
        
        return listJobs.toString();
        
        /*
        List<Job> listJobs = jobDAO.getMostRecent(Integer.parseInt(pagenumber),Integer.parseInt(entitieperpages));
        
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < listJobs.size()-1;i++)
            sb.append(listJobs.get(i).visiteurJson()+ ",\n");
        sb.append(listJobs.get(listJobs.size()-1).visiteurJson());
        sb.append("\n]");

        return sb.toString();
        */
    }

}
