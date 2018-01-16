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
    
    public IMPException(Status status) {
        this.httpStatus = status;
    }
    
    public Status getStatus(){
        return httpStatus;
    }
}
