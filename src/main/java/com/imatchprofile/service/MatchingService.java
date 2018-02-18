/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.MastersDao;
import com.imatchprofile.dao.MatchingDao;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Masters;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AmraniDriss
 */
 public class MatchingService extends Service{
    
    protected final  UserDAO userDao=new UserDAO();
    protected  final  MatchingDao matchingDao=new MatchingDao();
    protected final JobDAO jobDao=new JobDAO();
   
    
    
     public String matchingcandidatejob(String job_id,int user_id) throws IMPException{
       
      
         if(!isInteger(job_id ) || job_id==null)
            throw new IMPWrongURLParameterException();
        int job_idint = Integer.parseInt(job_id);
         
         User u = userDao.findById(user_id);
        Recruiter r = u.getRecruiter();
         
        if (r == null)
            throw new IMPNotACandidateException();
        
       Job j =jobDao.findOneById(job_idint);
       if(j==null)  throw new IMPException();
       
        List<Candidate> co = matchingDao.MatchingCandidateJob(job_idint);
       JSONArray listcandidat=new JSONArray();
          for(Candidate c : co){
            if (c.getVisibility() != 0)
                listcandidat.put(c.toJSONComplete());
        }
        
        return listcandidat.toString();
    }
}
