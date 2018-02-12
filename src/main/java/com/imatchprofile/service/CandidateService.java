/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;

/**
 *
 * @author Nihad
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
        if(!isInteger(Id) || Id == null)
            throw new IMPWrongURLParameterException();
            
        Candidate candidate = this.getCandidateDAO().findCandidateById(Integer.parseInt(Id));
        
        if (candidate == null)
            throw new IMPNoContentException();
        
        return candidate.toJSONComplete().toString();
    }
    
    public String getAll(String pagenumber, String entitieperpages) throws IMPException {
     
        if(!isInteger(pagenumber) || pagenumber == null || !isInteger(entitieperpages) || entitieperpages == null){
            throw new IMPWrongURLParameterException();
        }
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        JSONArray listCandidates = new JSONArray();
        
        for (Candidate c : candidateDAO.findAll(pgNum, entPerPg)) {
            if (c.getVisibility() != 0)
                listCandidates.put(c.visiteurJsonObject());
        }
        
        return listCandidates.toString();
    }

    
    public String getMyProfile(Integer userId) throws IMPException {
        User meUser = this.userDAO.findById(userId);
        Candidate meCandidate = meUser.getCandidate();
        
        if (meCandidate == null)
            throw new IMPNotACandidateException();
        
        return meCandidate.toJSONComplete().toString();
    }
    
    public String getcandidatesbytitle(String title, String pagenumber, String entitieperpages) throws IMPException {
        
        if(!isInteger(pagenumber) || !isInteger(entitieperpages))
            throw new IMPWrongURLParameterException();
        
        if(oneOfIsNull(title, pagenumber, entitieperpages))
            throw new IMPWrongURLParameterException();
        
        int pgNum = Integer.parseInt(pagenumber), entPerPg = Integer.parseInt(entitieperpages);
        
        if (pgNum == 0 || entPerPg == 0)
            throw new IMPNoContentException();
        
        JSONArray listCandidates = new JSONArray();
        
        for(Candidate can : candidateDAO.getCandidatByTitle(title, pgNum, entPerPg)) {
            if (can.getVisibility() != 0)
                listCandidates.put(can.visiteurJsonObject());
        }
       
        return listCandidates.toString();
    }
}
