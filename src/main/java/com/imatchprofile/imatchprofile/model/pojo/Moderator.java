package com.imatchprofile.imatchprofile.model.pojo;
// Generated 8 d�c. 2017 20:48:06 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Moderator generated by hbm2java
 */
@Entity
@Table(name="moderator"
    ,catalog="imatchprofile"
)
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
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="moderator_id", unique=true, nullable=false)
    public Integer getModeratorId() {
        return this.moderatorId;
    }
    
    public void setModeratorId(Integer moderatorId) {
        this.moderatorId = moderatorId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="candidate")
    public Set getUsers() {
        return this.users;
    }
    
    public void setUsers(Set users) {
        this.users = users;
    }




}


