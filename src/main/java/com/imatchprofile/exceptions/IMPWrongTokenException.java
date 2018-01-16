/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

/**
 *
 * @author j-m_d
 */
public class IMPWrongTokenException extends IMPUnauthorizedException {
    
    public IMPWrongTokenException() {
        super("Wrong token, unable to decrypt");
    }
    
}
