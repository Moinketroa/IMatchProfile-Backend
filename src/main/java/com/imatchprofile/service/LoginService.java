/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPBadFormatException;
import com.imatchprofile.exceptions.IMPEmailNotFoundException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPPayloadException;
import com.imatchprofile.exceptions.IMPWrongPassword;
import com.imatchprofile.helper.JWTHelper;
import com.imatchprofile.helper.PasswordHelper;
import com.imatchprofile.model.pojo.User;
import java.io.UnsupportedEncodingException;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MasterChief
 */
public class LoginService extends Service {
    
    private final UserDAO userDAO = new UserDAO();
    
    public String login(String content) throws IMPException {
        
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        
        String email, password;
        
        try {
            email = payload.getString("email");
            password = payload.getString("password");
        } catch (JSONException e) {
            throw new IMPPayloadException();
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(email, password))
            throw new IMPPayloadException();
        
        //verification de l'email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IMPBadFormatException("email");
        
        User userFound = userDAO.findOneByEmail(email);
        
        //verification si email trouvé
        if (userFound == null)
            throw new IMPEmailNotFoundException();
        
        //verification password
        if (!(userFound.getPassword().equals(PasswordHelper.encryptPassword(password))))
            throw new IMPWrongPassword();
        
        //creation token JWT
        String jwtToken;
        try {
            jwtToken = JWTHelper.createToken(userFound.getUserId());      
        } catch (UnsupportedEncodingException | IllegalArgumentException | JWTCreationException | NullPointerException ex) {
            System.out.println(ex.getMessage());
            throw new IMPInternalServerException("not able to create token");
        } 
        
        //creation de la reponse json
        JSONObject response = new JSONObject();
        response.put("token", jwtToken);
        response.put("user", userFound.toJSON());

        
        return response.toString();
    }
    
}
