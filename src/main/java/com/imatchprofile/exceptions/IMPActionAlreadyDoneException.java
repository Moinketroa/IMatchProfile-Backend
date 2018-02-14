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
public class IMPActionAlreadyDoneException extends IMPException {
    
    public IMPActionAlreadyDoneException() {
        super(Response.Status.PRECONDITION_FAILED);
        errorMessage = "Action already done, cannot redo this action";
    }
    
}
