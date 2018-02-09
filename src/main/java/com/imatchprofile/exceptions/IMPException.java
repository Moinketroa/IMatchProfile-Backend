/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

import javax.ws.rs.core.Response.Status;

/**
 *
 * @author MasterChief
 */
public class IMPException extends Exception {
    
    private Status httpStatus;
    protected String errorMessage;
    
    public IMPException(Status status) {
        this.httpStatus = status;
    }

    public IMPException() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public Status getStatus(){
        return httpStatus;
    }
    
    public String getErrorMessage() {
        return errorMessage;
    }
}
