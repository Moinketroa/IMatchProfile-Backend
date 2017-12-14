/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.imatchprofile.imatchprofile.controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import com.imatchprofile.imatchprofile.hibernatestuff.*;
import com.imatchprofile.imatchprofile.model.pojo.*;
/*
 *
 * @author AmraniDriss
 */
@Named(value = "theManagedBean")
@SessionScoped
public class TheManagedBean implements Serializable {

    private UserService users;
    public TheManagedBean() {
       
        users=new UserService();
    }

   

    public UserService getUsers() {
        return users;
    }

    public void setUsers(UserService users) {
        this.users = users;
    }


  

 
  
    
}
