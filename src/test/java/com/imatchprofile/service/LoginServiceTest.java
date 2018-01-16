/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

import com.imatchprofile.exceptions.IMPException;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author MasterChief
 */
public class LoginServiceTest {
    
    public LoginServiceTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of login method, of class LoginService.
     */
    @Test
    public void testLogin() throws Exception {
        System.out.println("login");

        LoginService instance = new LoginService();

        JSONObject rightContent = new JSONObject();
        rightContent.put("email", "test@test.com");
        rightContent.put("password", "testPassword");
        
        System.out.println(rightContent.toString());
        
        //json without email
        JSONObject contentWithoutEmail = new JSONObject(rightContent.toString());
        contentWithoutEmail.remove("email");
        
        try {
            instance.login(contentWithoutEmail.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        //json without password
        JSONObject contentWithoutPassword = new JSONObject(rightContent.toString());
        contentWithoutPassword.remove("password");
        
        try {
            instance.login(contentWithoutPassword.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        //wrong email format
        JSONObject wrongEmailFormat = new JSONObject(contentWithoutEmail.toString());
        wrongEmailFormat.put("email", "testNotAnEmail");
        
        try {
            instance.login(wrongEmailFormat.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        //email not found
        JSONObject emailNotFound = new JSONObject(contentWithoutEmail.toString());
        emailNotFound.put("email", "email_not_found@emailnotfound.com");
        
        try {
            instance.login(emailNotFound.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.UNAUTHORIZED, e.getStatus());
        }
        
        //wrong password
        JSONObject wrongPassword = new JSONObject(contentWithoutPassword.toString());
        wrongPassword.put("password", "testWrongPassword");
        
        try {
            instance.login(wrongPassword.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.UNAUTHORIZED, e.getStatus());
        }
    }
    
}
