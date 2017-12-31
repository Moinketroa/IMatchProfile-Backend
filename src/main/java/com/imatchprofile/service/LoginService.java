/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.imatchprofile.dao.UserDAO;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.model.pojo.User;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import javax.ws.rs.core.Response;
import org.apache.commons.validator.routines.EmailValidator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author MasterChief
 */
public class LoginService extends Service {
    
    private static final String SECRET = "3__v#LU_c-2R-J6#Mv-L2BMjK#_kh5r9y-6r7D59HD7fbs3#8vF55Rp--63-wc_La35dcSMqc93ZUDj-T-d-8HLPa-4_4_k-n_M85R#_gdrn2422##-Kha5FLR-#P_W-QzFFc3B4cc862";
    private final UserDAO userDAO = new UserDAO();
    
    public String login(String content) throws IMPException {
        
        //analyse du payload
        JSONObject payload = new JSONObject(content);
        
        String email, password;
        
        try {
            email = payload.getString("email");
            password = payload.getString("password");
        } catch (JSONException e) {
            throw new IMPException(Response.Status.BAD_REQUEST);
        }
        
        //verification de l'existence des champs
        if (oneOfIsNull(email, password))
            throw new IMPException(Response.Status.BAD_REQUEST);
        
        //verification de l'email
        if (!EmailValidator.getInstance().isValid(email))
            throw new IMPException(Response.Status.BAD_REQUEST);
        
        User userFound = userDAO.findOneByEmail(email);
        
        //verification si email trouvé
        if (userFound == null)
            throw new IMPException(Response.Status.UNAUTHORIZED);
        
        //verification password
        if (!(userFound.getPassword().equals(User.encryptPassword(password))))
            throw new IMPException(Response.Status.UNAUTHORIZED);
        
        //expiration
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        Date expirationDate = new Date(currentTime + (30 * 60000));
        
        //creation token JWT
        String jwtToken;
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            jwtToken = JWT.create()
                    .withClaim("user_id", userFound.getUserId())
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException | IllegalArgumentException | JWTCreationException ex) {
            throw new IMPException(Response.Status.INTERNAL_SERVER_ERROR);
        } 
        
        //creation de la reponse json
        JSONObject response = new JSONObject();
        response.put("token", jwtToken);
        response.put("user", userFound.toJSON());
        
        return response.toString();
    }
    
}
