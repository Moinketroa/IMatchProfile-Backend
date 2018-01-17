/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.util.List;

/**
 *
 * @author achyle
 */
public class RecruiterService extends UserService{
    
    public String signIn(String content) throws IMPException {
        String[] tabContent = this.signInToVerif(content);
        //creation du User
        User userRecruiter = new User(tabContent[0], tabContent[1], tabContent[2], tabContent[3]);
        userRecruiter.setPhotoUrl(tabContent[4]);
        this.getRecruiterDAO().create(userRecruiter);
        //reponse json
        return userRecruiter.getCandidate().toJSON().toString();
    }
    
    public String getAll(){
        List<Recruiter> listRecruiter = getRecruiterDAO().findAll();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i=0; i < listRecruiter.size()-1; i++)
            sb.append(listRecruiter.get(i).toJSON()+",\n");
        sb.append(listRecruiter.get(listRecruiter.size()-1).toJSON());
        sb.append("\n]");
        return sb.toString();
    }
    
    public String getMyProfile(Integer userId) throws IMPException {
        User meUser = this.userDAO.findById(userId);
        Recruiter meRecruiter = meUser.getRecruiter();
        
        if (meRecruiter == null)
            throw new IMPNotARecruiterException();
        
        return meRecruiter.toJSONComplete().toString();
    }
}
