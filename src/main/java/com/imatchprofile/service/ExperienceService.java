/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.ExperienceDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotAExperienceException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Experience;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import java.util.Calendar;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author brice
 */
public class ExperienceService { 
    private ExperienceDAO experienceDAO = new ExperienceDAO();
    private CandidateDAO candidateDAO = new CandidateDAO();
    private UserDAO userDAO = new UserDAO();
    
        public String postExperience(String content, Integer userId) throws IMPException {
       //analyse du payload
        JSONObject payload = new JSONObject(content);
        String title, description,company;
        String startDate;
        String endDate;
        Date sDate ;
        Date eDate ;
        Calendar calendar = Calendar.getInstance();
        
        try {
            title = payload.getString("title");
            description = payload.getString("description");
            company = payload.getString("company");
            startDate = payload.getString("startDate");
            endDate = payload.getString("endDate");
            
           
            String[] expStart = startDate.split("-");
            String[] expEnd = endDate.split("-");
            calendar.set(Integer.parseInt(expStart[0]),Integer.parseInt(expStart[1]),1);
            sDate= calendar.getTime();
            calendar.set(Integer.parseInt(expEnd[0]),Integer.parseInt(expEnd[1]),1);
            eDate= calendar.getTime();
            
            
           
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(title, description)) {
            throw new IMPPayloadException();
        }
        //recuperation du user authentifié
        User user = userDAO.findById(userId);
        //verification si id trouvé
        if (user == null)
            throw new IMPNotAUserException();
         
        //recuperation du candidat
        Candidate candidate = user.getCandidate();
        if(candidate == null)
               throw new IMPNotACandidateException();
         
        Experience experience = new Experience(candidate,title,description,sDate,eDate,company);
        experienceDAO.addExperience(experience);
        return experience.toJSON();
    }

    public void deleteExperience(String experienceId, Integer userId)throws IMPException {
         //verification user
        User meUser = this.userDAO.findById(userId);
        
        if (meUser == null)
            throw new IMPNotAUserException();
        
        //verification candidat
        Candidate meCandidate = meUser.getCandidate();
        
        if (meCandidate == null)
            throw new IMPNotACandidateException();
        
        Experience meExperience = experienceDAO.findById(Integer.parseInt(experienceId));
            
        if(meExperience == null)
            throw new IMPNotAExperienceException();
        
        experienceDAO.delete(meExperience);
    }
    
    public String editExperience(String content,String experienceId, Integer userId) throws IMPException {
       //analyse du payload
        JSONObject payload = new JSONObject(content);
        String title, description,company;
        String startDate;
        String endDate;
        Date sDate ;
        Date eDate ;
        Calendar calendar = Calendar.getInstance();
        
        try {
            title = payload.getString("title");
            description = payload.getString("description");
            company = payload.getString("company");
            startDate = payload.getString("startDate");
            endDate = payload.getString("endDate");
            
           
            String[] expStart = startDate.split("-");
            String[] expEnd = endDate.split("-");
            calendar.set(Integer.parseInt(expStart[0]),Integer.parseInt(expStart[1]),1);
            sDate= calendar.getTime();
            calendar.set(Integer.parseInt(expEnd[0]),Integer.parseInt(expEnd[1]),1);
            eDate= calendar.getTime();
            
            
           
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(title, description)) {
            throw new IMPPayloadException();
        }
        //recuperation du user authentifié
        User user = userDAO.findById(userId);
        //verification si id trouvé
        if (user == null)
            throw new IMPNotAUserException();
         
        //recuperation du candidat
        Candidate candidate = user.getCandidate();
        if(candidate == null)
               throw new IMPNotACandidateException();
        
        Experience meExperience = experienceDAO.findById(Integer.parseInt(experienceId));
        if(meExperience == null)
            throw new IMPNotAExperienceException();
        
        meExperience.setCompany(company);
        meExperience.setTitle(title);
        meExperience.setDescription(description);
        meExperience.setEndDate(eDate);
        meExperience.setStartDate(sDate);
        experienceDAO.editExperience(meExperience);
        return meExperience.toJSON();
    }
        
      
}
