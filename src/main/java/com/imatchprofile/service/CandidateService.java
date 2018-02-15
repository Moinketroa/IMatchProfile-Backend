/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPEmailAlreadyTakenException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNoContentException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotFoundEntityException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import java.io.UnsupportedEncodingException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nihad
 */
public class CandidateService extends UserService{
    
    public String signIn(String content) throws IMPException {
        String[] tabContent = this.signInToVerif(content);
        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(tabContent[2]);
        if (userFoundByEmail != null)
                throw new IMPEmailAlreadyTakenException();
        
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
            throw new IMPNotFoundEntityException("candidate");
        
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
    
    public String editBasicCandidate(String id, String content, Integer userId) throws IMPException {
        //verification du parametre
        if(!isInteger(id) || id == null)
            throw new IMPWrongURLParameterException();
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        // verification info propre a un user
        String[] tabContent = this.editUserVerif(payload,userId);
        String title, description, company;
        boolean visibility;
        try {
            title = payload.getString("title");
            description = payload.getString("description");
            company = payload.getString("company");
            visibility = payload.getBoolean("visibility");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(title, description, company, visibility))
            throw new IMPPayloadException();
        //recuperation du user authentifié
        Candidate candidateFound = candidateDAO.findCandidateById(userId);
        //verification si id trouvé
        if (candidateFound == null)
            throw new IMPNotACandidateException();
        candidateFound.getUser().setLastname(tabContent[0]);
        candidateFound.getUser().setFirstname(tabContent[1]);
        candidateFound.getUser().setEmail(tabContent[2]);
        candidateFound.getUser().setPhotoUrl(tabContent[3]);
        candidateFound.setTitle(title);
        candidateFound.setDescription(description);
        candidateFound.setCompany(company);
        byte tmpV = (byte) ((visibility)? 1 : 0);
        candidateFound.setVisibility(tmpV);
        userDAO.editUser(candidateFound.getUser());
        candidateDAO.editCandidate(candidateFound);
        return candidateFound.toJSON().toString();
    }
}
