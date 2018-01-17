/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author j-m_d
 */
public class IMPNoContentException extends IMPException {
    
    public IMPNoContentException() {
        super(Status.NO_CONTENT);
        errorMessage = "No content";
    }
    
}
