package com.imatchprofile.model.pojo;
// Generated 27 d�c. 2017 17:10:07 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;

/**
 * Moderator generated by hbm2java
 */
public class Moderator  implements java.io.Serializable {


     private Integer moderatorId;
     private User user;
     private Set users = new HashSet(0);

    public Moderator() {
    }

	
    public Moderator(User user) {
        this.user = user;
    }
    public Moderator(User user, Set users) {
       this.user = user;
       this.users = users;
    }
   
    public Integer getModeratorId() {
        return this.moderatorId;
    }
    
    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }




}


