/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNotACandidateException;
import com.imatchprofile.exceptions.IMPNotARecruiterException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongURLParameterException;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.helper.TokenHelper;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.Recruiter;
import com.imatchprofile.model.pojo.User;
import static com.imatchprofile.service.Service.oneOfIsNull;
import com.imatchprofile.util.HibernateUtil;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nihad
 */
public class RecruiterService extends UserService{
    
    public String signIn(String content) throws IMPException {
        String[] tabContent = this.signInToVerif(content);
        
        //creation du User
        User userRecruiter = new User(tabContent[0], tabContent[1], tabContent[2], tabContent[3]);
        userRecruiter.setPhotoUrl(tabContent[4]);
        this.getRecruiterDAO().create(userRecruiter);
        
        //reponse json
        String userJson = userRecruiter.getRecruiter().toJSON().toString();
        try {
            return TokenHelper.concatJsonsToken(userJson, "user", JWTHelper.createToken(userRecruiter.getUserId()));
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new IMPInternalServerException(ex.getMessage());
        }
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
    
    public String editBasicRecruiter(String id, String content, Integer userId) throws IMPException {
        //verification du parametre
        if(!isInteger(id) || id == null)
            throw new IMPWrongURLParameterException();
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        // verification info propre a un user
        String[] tabContent = this.editUserVerif(payload,userId);
        String description, company;
        try {
            description = payload.getString("description");
            company = payload.getString("company");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        //verification de l'existence des champs
        if (oneOfIsNull(description, company))
            throw new IMPPayloadException();
        //recuperation du user authentifié
        Recruiter recruiterFound = recruiterDAO.findRecruiterById(userId);
        System.out.println("XXXXXX === " + userId);
        //verification si id trouvé
        if (recruiterFound == null)
            throw new IMPNotARecruiterException();
        recruiterFound.getUser().setLastname(tabContent[0]);
        recruiterFound.getUser().setFirstname(tabContent[1]);
        recruiterFound.getUser().setEmail(tabContent[2]);
        recruiterFound.getUser().setPhotoUrl(tabContent[3]);
        recruiterFound.setDescription(description);
        recruiterFound.setCompany(company);
        userDAO.editUser(recruiterFound.getUser());
        recruiterDAO.editRecruiter(recruiterFound);
        return recruiterFound.toJSON().toString();
    }
}
