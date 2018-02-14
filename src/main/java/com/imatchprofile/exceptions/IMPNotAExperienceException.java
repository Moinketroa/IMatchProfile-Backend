/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.exceptions;

/**
 *
 * @author brice
 */
public class IMPNotAExperienceException extends IMPForbiddenException {
    public IMPNotAExperienceException() {
        super("Not an experience");
    }
}
