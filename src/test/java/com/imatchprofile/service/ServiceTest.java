/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.service;

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
public class ServiceTest {
    
    public ServiceTest() {
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
     * Test of oneOfIsNull method, of class Service.
     */
    @Test
    public void testOneOfIsNull() {
        System.out.println("oneOfIsNull");
        Object nullObject1 = null;
        Object nullObject2 = null;
        Object notNullObject1 = new Integer(0);
        Object notNullObject2 = new Character('c');
        
        boolean resultNullObject = Service.oneOfIsNull(nullObject1);
        assertEquals(true, resultNullObject);
        
        boolean resultNotNullObject = Service.oneOfIsNull(notNullObject1);
        assertEquals(false, resultNotNullObject);
        
        boolean resultTwoNullObjects = Service.oneOfIsNull(nullObject1, nullObject2);
        assertEquals(true, resultTwoNullObjects);
        
        boolean resultTwoNotNullObjects = Service.oneOfIsNull(notNullObject1, notNullObject2);
        assertEquals(false, resultTwoNotNullObjects);
        
        boolean resultOneNullObjectOneNotNullObject = Service.oneOfIsNull(notNullObject1, nullObject2);
        assertEquals(true, resultOneNullObjectOneNotNullObject);
        
        boolean resultOneNullObjectTwoNotNullObject = Service.oneOfIsNull(notNullObject2, nullObject1, notNullObject1);
        assertEquals(true, resultOneNullObjectTwoNotNullObject);
    }
    
}
