/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.JobDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPExpiredTokenException;
import com.imatchprofile.exceptions.IMPNoTokenException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPNotAUserException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongTokenException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.model.pojo.Job;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;

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
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
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
        
        Job newJob = new Job(recruiter, title, description, (byte) 1, new Date());
        
        //
        
        return "{}";
    }
    
}
