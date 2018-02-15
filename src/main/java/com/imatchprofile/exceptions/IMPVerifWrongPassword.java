/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

/**
 *
 * @author achyle
 */
public class IMPVerifWrongPassword extends IMPUnauthorizedException {
    
    public IMPVerifWrongPassword() {
        super("verification wrong password");
    }
    
}
