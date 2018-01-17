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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author j-m_d
 */
public class TokenHelper {
 
    public static TokenHelperResult verifyOptionalAndRefresh(String tokenBearer) throws IMPException {
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
                    return new TokenHelperResult(token, userId);
                }
            }
        }
        
        return new TokenHelperResult(null, null);
    }
    
    public static TokenHelperResult verifyNeededAndRefresh(String tokenBearer) throws IMPException {
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

        return new TokenHelperResult(token, userId);
    }
    
    public static String concatJsonsToken(String jsonResponse, String responseName, String token) {
        JSONObject result = new JSONObject();
        
        try {
            result.put(responseName, new JSONObject(jsonResponse));
        } catch (JSONException e) {
            //weird case if jsonResponse is an array
            result.put(responseName, new JSONArray(jsonResponse));
        }
        
        if (token != null) {
            result.put("token", token);
        }
        
        return result.toString();
    }
}
