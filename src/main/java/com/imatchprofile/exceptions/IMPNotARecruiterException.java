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
public class IMPNotARecruiterException extends IMPForbiddenException {
    
    public IMPNotARecruiterException() {
        super("Token decoded : not a recruiter");
    }
    
}
