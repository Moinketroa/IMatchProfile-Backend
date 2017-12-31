/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.dao.CandidateDAO;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.model.pojo.User;
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
            throw new IMPException(Response.Status.BAD_REQUEST);
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(lastname, firstname, email, password, photoUrl))
            throw new IMPException(Response.Status.BAD_REQUEST);
        
        //verification de l'email et de l'url de la photo
        if (!EmailValidator.getInstance().isValid(email) || 
            !UrlValidator.getInstance().isValid(photoUrl))
            throw new IMPException(Response.Status.BAD_REQUEST);
        
        //verification si email déjà présent
        User userFoundByEmail = userDAO.findOneByEmail(email);
        if (userFoundByEmail != null)
            throw new IMPException(Response.Status.PRECONDITION_FAILED);
        
        //chiffrage du password
        password = User.encryptPassword(password);
        
        //creation du User
        User userCandidate = new User(lastname, firstname, email, password);
        userCandidate.setPhotoUrl(photoUrl);
        
        candidateDAO.create(userCandidate);

        //reponse json
        return userCandidate.getCandidate().toJSON().toString();
    }
    
    
}
