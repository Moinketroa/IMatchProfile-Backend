/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.AppliesDAO;
import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPActionAlreadyDoneException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPNotFoundEntityException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.model.pojo.Applies;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author j-m_d
 */
public class ApplyService extends Service {
    
    private final UserDAO userDAO = new UserDAO();
    private final JobDAO jobDAO = new JobDAO();
    private final AppliesDAO appliesDAO = new AppliesDAO();
    
    public String applyToJob(String idJob, Integer userId) throws IMPException {
        //verification parametre url
        if(!isInteger(idJob) || idJob == null)
            throw new IMPWrongURLParameterException();
        
        Integer jobID = Integer.parseInt(idJob);
        
        //verification user
        User user = this.userDAO.findById(userId);
        
        if (user == null)
            throw new IMPNotAUserException();
        
        //verification candidat
        Candidate candidate = user.getCandidate();
        
        if (candidate == null)
            throw new IMPNotACandidateException();
        
        //verification job
        Job job = this.jobDAO.findOneById(jobID);
        
        if (job == null)
            throw new IMPNotFoundEntityException("job");
        
        //verification si pas deja postule
        for (Object _applyObj : candidate.getApplieses()){
            Applies _apply = (Applies) _applyObj;
            
            if (_apply.getJob().getJobId().equals( jobID))
                throw new IMPActionAlreadyDoneException();
        }
        
        Applies apply = new Applies(candidate, job);
        
        appliesDAO.create(apply);
        
        return apply.toJSON().toString();
    }
    
    public void removeApply(String idJob, Integer idUser) throws IMPException {
        //verification parametre url
        if(!isInteger(idJob) || idJob == null)
            throw new IMPWrongURLParameterException();
        
        Integer idJobInteger = Integer.parseInt(idJob);
        
        //verification user
        User user = this.userDAO.findById(idUser);
        
        if (user == null)
            throw new IMPNotAUserException();
        
        //verification candidat
        Candidate candidate = user.getCandidate();
        
        if (candidate == null)
            throw new IMPNotACandidateException();
        
        for (Object _applyO : candidate.getApplieses()) {
            Applies _apply = (Applies) _applyO;
            
            if (_apply.getJob().getJobId().equals(idJobInteger))
                appliesDAO.delete(_apply);
        }
    }

    public String getApplyCandidate(String idJob, String pagenumber, String entitieperpages,Integer userId) throws IMPException{
        if(!isInteger(idJob) || idJob == null)
            throw new IMPWrongURLParameterException();
        
        if(!isInteger(pagenumber) || pagenumber == null || !isInteger(entitieperpages) || entitieperpages == null){
            throw new IMPWrongURLParameterException();
        }
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        Integer idJobInteger = Integer.parseInt(idJob);
        
        //verification user
        User user = this.userDAO.findById(userId);
        
        if (user == null)
            throw new IMPNotAUserException();
        
        //verification candidat
       Recruiter recruiter = user.getRecruiter();
        
        if (recruiter == null)
            throw new IMPNotARecruiterException();
        
       JSONArray listCandidates = new JSONArray();
        
        for (Candidate c : this.appliesDAO.getCandidateByJobs(idJob,pgNum, entPerPg)) {
            if (c.getVisibility() != 0)
                listCandidates.put(c.visiteurJsonObject());
        }
        
        return listCandidates.toString();
    }
}
