/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author j-m_d
 */
public class IMPPayloadException extends IMPException {
    
    public IMPPayloadException() {
        super(Status.BAD_REQUEST);
        errorMessage = "Missing parameter in Payload";
    }
    
}
