/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.model.pojo;

/**
 *
 * @author MasterChief
 */
public enum Role {
    
    CANDIDATE("C"),
    RECRUITER("R"),
    MODERATOR("M");
    
    private String value;
    
    private Role(final String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
