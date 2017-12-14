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
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name="user"
    ,catalog="imatchprofile"
    , uniqueConstraints = @UniqueConstraint(columnNames="email") 
)
public class User  implements java.io.Serializable {


     private Integer userId;
     private Candidate candidate;
     private Moderator moderator;
     private Recruiter recruiter;
     private String lastname;
     private String firstname;
     private String email;
     private String password;
     private String photoUrl;
     private String role;
     private Set candidates = new HashSet(0);
     private Set candidatereportses = new HashSet(0);
     private Set recruiters = new HashSet(0);
     private Set jobreportses = new HashSet(0);
     private Set moderators = new HashSet(0);

    public User() {
    }

	
    public User(String lastname, String firstname, String email, String password) {
        this.lastname = lastname;
        this.firstname = firstname;
        this.email = email;
        this.password = password;
    }
    public User(Candidate candidate, Moderator moderator, Recruiter recruiter, String lastname, String firstname, String email, String password, String photoUrl, String role, Set candidates, Set candidatereportses, Set recruiters, Set jobreportses, Set moderators) {
       this.candidate = candidate;
       this.moderator = moderator;
       this.recruiter = recruiter;
       this.lastname = lastname;
       this.firstname = firstname;
       this.email = email;
       this.password = password;
       this.photoUrl = photoUrl;
       this.role = role;
       this.candidates = candidates;
       this.candidatereportses = candidatereportses;
       this.recruiters = recruiters;
       this.jobreportses = jobreportses;
       this.moderators = moderators;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="user_id", unique=true, nullable=false)
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sub_user_id")
    public Candidate getCandidate() {
        return this.candidate;
    }
    
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sub_user_id", insertable=false, updatable=false)
    public Moderator getModerator() {
        return this.moderator;
    }
    
    public void setModerator(Moderator moderator) {
        this.moderator = moderator;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="sub_user_id", insertable=false, updatable=false)
    public Recruiter getRecruiter() {
        return this.recruiter;
    }
    
    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }

    
    @Column(name="lastname", nullable=false, length=45)
    public String getLastname() {
        return this.lastname;
    }
    
    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    
    @Column(name="firstname", nullable=false, length=45)
    public String getFirstname() {
        return this.firstname;
    }
    
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    
    @Column(name="email", unique=true, nullable=false, length=45)
    public String getEmail() {
        return this.email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }

    
    @Column(name="password", nullable=false, length=45)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="photoUrl", length=45)
    public String getPhotoUrl() {
        return this.photoUrl;
    }
    
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    
    @Column(name="role", length=45)
    public String getRole() {
        return this.role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getCandidates() {
        return this.candidates;
    }
    
    public void setCandidates(Set candidates) {
        this.candidates = candidates;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getCandidatereportses() {
        return this.candidatereportses;
    }
    
    public void setCandidatereportses(Set candidatereportses) {
        this.candidatereportses = candidatereportses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getRecruiters() {
        return this.recruiters;
    }
    
    public void setRecruiters(Set recruiters) {
        this.recruiters = recruiters;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getJobreportses() {
        return this.jobreportses;
    }
    
    public void setJobreportses(Set jobreportses) {
        this.jobreportses = jobreportses;
    }

@OneToMany(fetch=FetchType.LAZY, mappedBy="user")
    public Set getModerators() {
        return this.moderators;
    }
    
    public void setModerators(Set moderators) {
        this.moderators = moderators;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", candidate=" + candidate + ", moderator=" + moderator + ", recruiter=" + recruiter + ", lastname=" + lastname + ", firstname=" + firstname + ", email=" + email + ", password=" + password + ", photoUrl=" + photoUrl + ", role=" + role + ", candidates=" + candidates + ", candidatereportses=" + candidatereportses + ", recruiters=" + recruiters + ", jobreportses=" + jobreportses + ", moderators=" + moderators + '}';
    }




}


