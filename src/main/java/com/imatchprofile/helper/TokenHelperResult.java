/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.helper;

/**
 *
 * @author j-m_d
 */
public class TokenHelperResult {
    
    private String newToken;
    private Integer userId;
    
    public TokenHelperResult(String token, Integer id) {
        newToken = token;
        userId = id;
    }
    
    public String getNewToken() {
        return newToken;
    }

    public Integer getUserId() {
        return userId;
    }
}
