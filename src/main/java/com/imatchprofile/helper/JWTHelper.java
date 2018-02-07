/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author j-m_d
 */
public class JWTHelper {
    
    private static final String SECRET = "3__v#LU_c-2R-J6#Mv-L2BMjK#_kh5r9y-6r7D59HD7fbs3#8vF55Rp--63-wc_La35dcSMqc93ZUDj-T-d-8HLPa-4_4_k-n_M85R#_gdrn2422##-Kha5FLR-#P_W-QzFFc3B4cc862";
    
    public static String createToken(Integer userId) throws IllegalArgumentException, UnsupportedEncodingException {
        Calendar calendar = Calendar.getInstance();
        long currentTime = calendar.getTimeInMillis();
        Date expirationDate = new Date(currentTime + (30 * 60000));
        
        Algorithm algorithm = Algorithm.HMAC256(JWTHelper.SECRET);
        return JWT.create()
                    .withClaim("user_id", userId)
                    .withExpiresAt(expirationDate)
                    .sign(algorithm);
    }
    
    public static Integer decrypt(String token) throws IllegalArgumentException, UnsupportedEncodingException, JWTDecodeException {
        Algorithm algorithm = Algorithm.HMAC256(JWTHelper.SECRET);
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decoded;
        
        try {
            decoded = verifier.verify(token);
        } catch (TokenExpiredException ex) {
            return null;
        }
        
        Claim claim = decoded.getClaim("user_id");
        return claim.asInt();
    }
    
    public static String encrypt(String value) throws IllegalArgumentException, UnsupportedEncodingException {
        Algorithm algorithm = Algorithm.HMAC256(JWTHelper.SECRET);
        return JWT.create()
                    .withClaim("password", value)
                    .sign(algorithm);
    }
}
