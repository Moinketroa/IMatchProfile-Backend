/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

/**
 *
 * @author MasterChief
 */
public class Service {
    
    public static boolean oneOfIsNull(Object... objects){
        for (Object o : objects)
            if (o == null)
                return true;
        return false;
    }
    
    public boolean isInteger(String s) {
        try { 
            Integer.parseInt(s); 
        } catch(NumberFormatException | NullPointerException e) { 
            return false; 
        }
        // only got here if we didn't return false
        return true;
    }
 
}
