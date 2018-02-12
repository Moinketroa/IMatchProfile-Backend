/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

import javax.ws.rs.core.Response;

/**
 *
 * @author j-m_d
 */
public class IMPNotFoundEntityException extends IMPException {
    
    public IMPNotFoundEntityException(String entity) {
        super(Response.Status.NOT_FOUND);
        
        this.errorMessage = entity + " not found";
    }
    
}
