/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.TraningDao;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.DateHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.Training;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import java.util.Date;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AmraniDriss
 */
public class TrainingService extends Service{
       
        protected final UserDAO userDAO = new UserDAO();
          protected final TraningDao trainingDAO = new TraningDao();
     public String addTraining(String content,int user_id) throws IMPException{
         JSONObject payload = new JSONObject(content);
          String title;
          String description;
          String institute;
          String start_date;
          String end_date;
         
          try {
            title = payload.getString("title");
            description = payload.getString("description");
            institute = payload.getString("institute");
            start_date = payload.getString("start_date");
            end_date = payload.getString("end_date");
        } catch (JSONException e) {
            
            throw new IMPPayloadException();
        }
       
        if (oneOfIsNull(title,institute,start_date,end_date,description))
            throw new IMPPayloadException();
       
        User user = userDAO.findById(user_id);
        Candidate candidate = user.getCandidate();
        if (candidate == null)
            throw new IMPNotACandidateException();
        
       Date startDate1=  DateHelper.converStringToDate(start_date);
       Date endDate1=  DateHelper.converStringToDate(end_date);
        if(startDate1==null || endDate1==null)
            throw new IMPException();
        Training training = new Training();
        training.setDescription(description);
        training.setStartDate(startDate1);
        training.setCandidate(candidate);
        training.setTitle(title);
        training.setEndDate(endDate1);
        training.setInstitute(institute);
      Training  s = trainingDAO.AddTraining(candidate, training);
        return s.toJSON();
    }
     
     public String deleteTraining(int user_id,String training_id) throws IMPException{
         int training_idint;
         
         if(!isInteger(training_id ) || training_id==null)
            throw new IMPWrongURLParameterException();
         
         training_idint=Integer.parseInt(training_id);
          User user = userDAO.findById(user_id);
        Candidate candidate = user.getCandidate();
        if (candidate == null)
            throw new IMPNotARecruiterException();
        Training training = trainingDAO.Search( candidate.getCandidateId(),training_idint);
        if(training!=null){
          trainingDAO.DeleteTraining(candidate, training);
        }
        else throw new IMPException();
        
        return training.toJSON();
     }
    
}
