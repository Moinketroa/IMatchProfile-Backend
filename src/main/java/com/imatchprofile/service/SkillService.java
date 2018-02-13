/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.SkillDao;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Skill;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author AmraniDriss
 */
public class SkillService {
    
    protected final  CandidateDAO candidateDAO=new CandidateDAO();
    protected  final  SkillDao skillDAO=new SkillDao();
    
    public String addSkill(String content) throws IMPException{
        JSONObject payload = new JSONObject(content);
        int user_id;
        String skillname;
           try {
            user_id = payload.getInt("candidate_id");
            skillname = payload.getString("skill");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
      
        if (oneOfIsNull(user_id, skillname))
            throw new IMPPayloadException();
       
        Candidate c = candidateDAO.findCandidateById(user_id);
  
        if (c == null)
            throw new IMPNotACandidateException();
        
      Skill s = skillDAO.AddSkillToCandidat(c.getCandidateId(), skillname);
        return s.toJSON().toString();
    }
    
    
     public String deleteSkill(String content) throws IMPException{
        JSONObject payload = new JSONObject(content);
        int user_id;
        int skill_id;
           try {
            user_id = payload.getInt("candidate_id");
            skill_id = payload.getInt("skill_id");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
      
        if (oneOfIsNull(user_id, skill_id))
            throw new IMPPayloadException();
       
        Candidate c = candidateDAO.findCandidateById(user_id);
  
        if (c == null)
            throw new IMPNotACandidateException();
        
      Candidate co = skillDAO.deleteSkill(skill_id,c.getCandidateId());
        return co.toJSON().toString();
    }
}
