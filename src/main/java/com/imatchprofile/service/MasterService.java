/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.MastersDao;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Masters;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AmraniDriss
 */
public class MasterService extends Service{
    
    protected final  UserDAO userDao=new UserDAO();
    protected  final  MastersDao masterDao=new MastersDao();
    
    public String addSkill(String content,int user_id) throws IMPException{
        JSONObject payload = new JSONObject(content);
  
        String skillname;
           try {
         
            skillname = payload.getString("skill");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
      
        if (oneOfIsNull(skillname))
            throw new IMPPayloadException();
       
        User u = userDao.findById(user_id);
        Candidate c = u.getCandidate();
        if (c == null)
            throw new IMPNotACandidateException();
        
      Skill s = masterDao.AddSkillToCandidat(c.getCandidateId(), skillname);
        return s.toJSON().toString();
    }
    
    
     public String deleteSkill(String skill_id,int user_id) throws IMPException{
       
      
         if(!isInteger(skill_id ) || skill_id==null)
            throw new IMPWrongURLParameterException();
       int skillint = Integer.parseInt(skill_id);
         
         User u = userDao.findById(user_id);
        Candidate c = u.getCandidate();
         
        if (c == null)
            throw new IMPNotACandidateException();
        
       Masters m= masterDao.Search(c.getCandidateId(), skillint);
       if(m==null)  throw new IMPException();
      Candidate co = masterDao.deleteSkill(skillint,c.getCandidateId());
        return co.toJSON().toString();
    }
}
