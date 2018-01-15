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
public class CandidateServiceTest {
    
    public CandidateServiceTest() {
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
     * Test of signIn method, of class CandidateService.
     */
    @Test
    public void testSignIn() throws Exception {
        System.out.println("signIn");
        
        CandidateService instance = new CandidateService();
        
        JSONObject rightContent = new JSONObject();
        rightContent.put("lastname", "testLastName");
        rightContent.put("firstname", "testFirstName");
        rightContent.put("email", "test@test.com");
        rightContent.put("password", "testPassword");
        rightContent.put("photoUrl", "http://test.com");
        
        JSONObject contentWithoutLastname = new JSONObject(rightContent.toString());
        contentWithoutLastname.remove("lastname");
        
        
        try {
            instance.signIn(contentWithoutLastname.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWithoutFirstname = new JSONObject(rightContent.toString());
        contentWithoutFirstname.remove("firstname");
        
        try {
            instance.signIn(contentWithoutFirstname.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWithoutEmail = new JSONObject(rightContent.toString());
        contentWithoutEmail.remove("email");
        
        try {
            instance.signIn(contentWithoutEmail.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWithoutPassword = new JSONObject(rightContent.toString());
        contentWithoutPassword.remove("password");
        
        try {
            instance.signIn(contentWithoutPassword.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWithoutPhotoUrl = new JSONObject(rightContent.toString());
        contentWithoutPhotoUrl.remove("photoUrl");
        
        try {
            instance.signIn(contentWithoutPhotoUrl.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWrongEmail = new JSONObject(contentWithoutEmail.toString());
        contentWrongEmail.put("email", "testNotAnEmail");
        
        try {
            instance.signIn(contentWrongEmail.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        JSONObject contentWrongUrl = new JSONObject(contentWithoutPhotoUrl.toString());
        contentWrongUrl.put("photoUrl", "testNotAURL");
        
        try {
            instance.signIn(contentWrongUrl.toString());
            fail();
        } catch (IMPException e) {
            assertEquals(Response.Status.BAD_REQUEST, e.getStatus());
        }
        
        /* ne pas effacer
        try {
            instance.signIn(rightContent.toString());
        } catch (IMPException e) {
            assertEquals(Response.Status.PRECONDITION_FAILED, e.getStatus());
        }
        */
    }
    
}
