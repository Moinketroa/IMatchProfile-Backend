/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Moderator;
import com.imatchprofile.model.pojo.User;

/**
 *
 * @author j-m_d
 */
public class ModeratorService extends UserService {
    
    public String getMyProfile(Integer userId) throws IMPException {
        User meUser = this.userDAO.findById(userId);
        Moderator meModerator = meUser.getModerator();
        
        if (meModerator == null)
            throw new IMPNotACandidateException();
        
        return meModerator.toJSONComplete().toString();
    }
    
}
