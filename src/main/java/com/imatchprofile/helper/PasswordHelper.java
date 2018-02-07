/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

import com.imatchprofile.exceptions.IMPInternalServerException;
import java.io.UnsupportedEncodingException;
import com.imatchprofile.exceptions.IMPException;

/**
 *
 * @author j-m_d
 */
public class PasswordHelper {
    
    private static String SALT = "#x#542j7#Z3-J84EU-W#dxxDSD7s#m_4f";
    
    public static String encryptPassword(String password) throws IMPException {
        try {
            return JWTHelper.encrypt(password + SALT);
        } catch (IllegalArgumentException | UnsupportedEncodingException ex) {
            throw new IMPInternalServerException("Not able to encrypt password");
        }
    }
    
}
