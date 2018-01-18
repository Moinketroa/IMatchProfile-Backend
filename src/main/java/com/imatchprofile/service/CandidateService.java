/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 *
 * @author achyle
 */
public class CandidateService extends UserService{
    
    public String signIn(String content) throws IMPException {
        String[] tabContent = this.signInToVerif(content);
        //creation du User
        User userCandidate = new User(tabContent[0], tabContent[1], tabContent[2], tabContent[3]);
        userCandidate.setPhotoUrl(tabContent[4]);
        this.getCandidateDAO().create(userCandidate);
        
        //reponse json
        String userJson = userCandidate.getCandidate().toJSON().toString();
        try {
            return TokenHelper.concatJsonsToken(userJson, "user", JWTHelper.createToken(userCandidate.getUserId()));
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new IMPInternalServerException(ex.getMessage());
        }
    }
    
    public String getProfilById(String Id) throws IMPException{
        if(!isInteger(Id) || Id == null){
            throw new IMPPayloadException();
        }
        Candidate candidate = this.getUserDAO().findById(Integer.parseInt(Id)).getCandidate();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return candidate.profilJson();
    }
    
    public String getAll(){
        List<Candidate> listCandidate = getCandidateDAO().findAll();
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i=0; i < listCandidate.size()-1; i++)
            sb.append(listCandidate.get(i).toJSON()+",\n");
        sb.append(listCandidate.get(listCandidate.size()-1).toJSON());
        sb.append("\n]");
        return sb.toString();
    }

    
    public String getMyProfile(Integer userId) throws IMPException {
        User meUser = this.userDAO.findById(userId);
        Candidate meCandidate = meUser.getCandidate();
        
        if (meCandidate == null)
            throw new IMPNotACandidateException();
        
        return meCandidate.toJSONComplete().toString();
    }
    public String getcandidatesbytitle(String title){
          List<Candidate> listCandidate = getCandidateDAO().getUserCandidat(title);
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i=0; i < listCandidate.size()-1; i++)
            sb.append(listCandidate.get(i).toJSON()+",\n");
        sb.append(listCandidate.get(listCandidate.size()-1).toJSON());
        sb.append("\n]");
        return sb.toString();
    }
}
