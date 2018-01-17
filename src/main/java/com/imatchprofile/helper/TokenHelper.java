/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import com.auth0.jwt.exceptions.JWTDecodeException;
import com.imatchprofile.exceptions.IMPException;
import com.imatchprofile.exceptions.IMPExpiredTokenException;
import com.imatchprofile.exceptions.IMPInternalServerException;
import com.imatchprofile.exceptions.IMPNoTokenException;
import com.imatchprofile.exceptions.IMPWrongTokenException;
import java.io.UnsupportedEncodingException;
import org.json.JSONObject;

/**
 *
 * @author j-m_d
 */
public class TokenHelper {
 
    public static String verifyOptionalAndRefresh(String tokenBearer) throws IMPException {
        if (tokenBearer != null) {
            String token = tokenBearer.replace("Bearer ", "");
            if (token != null) {
                //verification et recuperation id
                Integer userId = null;
                try {
                    userId = JWTHelper.decrypt(token);
                } catch (IllegalArgumentException | UnsupportedEncodingException | JWTDecodeException ex) {
                    
                }
                
                if (userId != null) {
                    //generation du nouveau token
                    try {
                        token = JWTHelper.createToken(userId);
                    } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
                        throw new IMPInternalServerException(ex.getMessage());
                    }
                    return token;
                }
            }
        }
        
        return null;
    }
    
    public static Object[] verifyNeededAndRefresh(String tokenBearer) throws IMPException {
        //verification et traitement de la valeur de l'header
        if (tokenBearer == null) {
            throw new IMPNoTokenException();
        }
        
        String token = tokenBearer.replace("Bearer ", "");
        if (token == null) {
            throw new IMPNoTokenException();
        }
        
        //verification et recuperation id
        Integer userId = null;
        try {
            userId = JWTHelper.decrypt(token);
        } catch (IllegalArgumentException | UnsupportedEncodingException | JWTDecodeException ex) {
            throw new IMPWrongTokenException();
        }
        if (userId == null) {
            throw new IMPExpiredTokenException();
        }
        
        //generation du nouveau token
        try {
            token = JWTHelper.createToken(userId);
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new IMPInternalServerException(ex.getMessage());
        }
        
        Object[] result = new Object[2];
        result[0] = token;
        result[1] = userId;
        return result;
    }
    
    public static String concatJsonsToken(String jsonResponse, String responseName, String token) {
        JSONObject response = new JSONObject(jsonResponse);
        JSONObject result = new JSONObject();
        result.put(responseName, response);
        
        if (token != null) {
            result.put("token", token);
        }
        
        return result.toString();
    }
}
