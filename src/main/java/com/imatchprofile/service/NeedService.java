/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.NeedDao;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Needs;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AmraniDriss
 */
public class NeedService extends Service{
      protected final  JobDAO jobDao=new JobDAO();
    protected  final  NeedDao needDao=new NeedDao();
    protected final UserDAO userDAO = new UserDAO();
    
    public String addSkill(String content,int user_id) throws IMPException{
        JSONObject payload = new JSONObject(content);
        int job_id;
        String skillname;
           try {
            job_id = payload.getInt("job_id");
            skillname = payload.getString("skill");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
      
        if (oneOfIsNull(job_id, skillname))
            throw new IMPPayloadException();
       
        Job c = jobDao.findOneById(job_id);
        User user = userDAO.findById(user_id);
        Recruiter recruiter = user.getRecruiter();
        if (recruiter == null)
            throw new IMPNotARecruiterException();
         Job job =jobDao.Search(recruiter.getRecruiterId(),job_id);
        if(job==null) throw  new IMPException();
      Skill s = needDao.AddSkillToJob(c.getJobId(), skillname);
        return s.toJSON().toString();
    }
    
    
     public String deleteSkill(String job_id,String skill_id,int user_id) throws IMPException{
         
         if(!isInteger(skill_id ) || skill_id==null || !isInteger(job_id ) || job_id==null)
            throw new IMPWrongURLParameterException();
         int job_idint=Integer.parseInt(job_id);
        int skill_idint=Integer.parseInt(skill_id);
           
        if (oneOfIsNull(job_id, skill_id))
            throw new IMPPayloadException();
       
        Job c = jobDao.findOneById(job_idint);
  
        User user = userDAO.findById(user_id);
        Recruiter recruiter = user.getRecruiter();
        if (recruiter == null)
            throw new IMPNotARecruiterException();
         Needs m= needDao.Search(job_idint, skill_idint);
        Job job =jobDao.Search(recruiter.getRecruiterId(),job_idint);
        
       if(m==null || job==null)  throw new IMPException();
      Job co = needDao.deleteSkill(skill_idint,c.getJobId());
        return co.toJson().toString();
    }
}
