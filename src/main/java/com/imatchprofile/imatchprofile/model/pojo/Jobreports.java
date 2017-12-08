package com.imatchprofile.imatchprofile.model.pojo;
// Generated 8 d�c. 2017 20:48:06 by Hibernate Tools 4.3.1


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Jobreports generated by hbm2java
 */
@Entity
@Table(name="jobreports"
    ,catalog="imatchprofile"
)
public class Jobreports  implements java.io.Serializable {


     private Integer jobreportsId;
     private Job job;
     private User user;

    public Jobreports() {
    }

    public Jobreports(Job job, User user) {
       this.job = job;
       this.user = user;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="jobreports_id", unique=true, nullable=false)
    public Integer getJobreportsId() {
        return this.jobreportsId;
    }
    
    public void setJobreportsId(Integer jobreportsId) {
        this.jobreportsId = jobreportsId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="job_id", nullable=false)
    public Job getJob() {
        return this.job;
    }
    
    public void setJob(Job job) {
        this.job = job;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }




}


