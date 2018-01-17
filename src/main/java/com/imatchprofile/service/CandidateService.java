/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPBadFormatException;
import com.imatchprofile.exceptions.IMPEmailAlreadyTakenException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.model.pojo.Candidate;
import com.imatchprofile.model.pojo.User;
import com.imatchprofile.util.HibernateUtil;
import javax.ws.rs.core.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.UrlValidator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MasterChief
 */
public class CandidateService extends Service {

    private final CandidateDAO candidateDAO = new CandidateDAO();
    private final UserDAO userDAO = new UserDAO();
    
    public String signIn(String content) throws IMPException {
        
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        
        String lastname, firstname, email, password, photoUrl;
        
        try {
            lastname = payload.getString("lastname");
            firstname = payload.getString("firstname");
            email = payload.getString("email");
            password = payload.getString("password");
            photoUrl = payload.getString("photoUrl");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(lastname, firstname, email, password, photoUrl))
            throw new IMPPayloadException();
        
        //verification de l'email et de l'url de la photo
        if (!EmailValidator.getInstance().isValid(email) || 
            !UrlValidator.getInstance().isValid(photoUrl))
            throw new IMPBadFormatException("email");
        
        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(email);
        if (userFoundByEmail != null)
            throw new IMPEmailAlreadyTakenException();
        
        //chiffrage du password
        password = User.encryptPassword(password);
        
        //creation du User
        User userCandidate = new User(lastname, firstname, email, password);
        userCandidate.setPhotoUrl(photoUrl);
        
        candidateDAO.create(userCandidate);

        //reponse json
        return userCandidate.getCandidate().toJSON().toString();
    }
    
    public String getProfilById(String Id) throws IMPException{
        if(!isInteger(Id) || Id == null){
            throw new IMPPayloadException();
        }
        Candidate candidate = candidateDAO.findOneById(Integer.parseInt(Id));
        HibernateUtil.getSessionFactory().getCurrentSession().close();
        return candidate.profilJson();
    }
    
    
}
