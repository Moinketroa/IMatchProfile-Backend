package com.imatchprofile.model.pojo;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Job generated by hbm2java
 */
public class Job  implements java.io.Serializable {

     private Integer jobId;
     private Recruiter recruiter;
     private String title;
     private String description;
     private byte visibility;
     private Date createDate;

   
     private Set applieses = new HashSet(0);
     private Set needses = new HashSet(0);
     private Set matcheses = new HashSet(0);
     private Set jobreportses = new HashSet(0);

    public Job() {
    }

	
    public Job(Recruiter recruiter, String title, String description, byte visibility, Date createDate) {
        this.recruiter = recruiter;
        this.title = title;
        this.description = description;
        this.visibility = visibility;
        this.createDate = createDate;
    }
    public Job(Recruiter recruiter, String title, String description, byte visibility, Date createDate, Set applieses, Set needses, Set matcheses, Set jobreportses) {
       this.recruiter = recruiter;
       this.title = title;
       this.description = description;
       this.visibility = visibility;
       this.createDate = createDate;
       this.applieses = applieses;
       this.needses = needses;
       this.matcheses = matcheses;
       this.jobreportses = jobreportses;
    }
   
    public Integer getJobId() {
        return this.jobId;
    }
    
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
    public Recruiter getRecruiter() {
        return this.recruiter;
    }
    
    public void setRecruiter(Recruiter recruiter) {
        this.recruiter = recruiter;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return this.description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    public byte getVisibility() {
        return this.visibility;
    }
    
    public void setVisibility(byte visibility) {
        this.visibility = visibility;
    }
    public Date getCreateDate() {
        return this.createDate;
    }
    
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Set getApplieses() {
        return this.applieses;
    }
    
    public void setApplieses(Set applieses) {
        this.applieses = applieses;
    }
    public Set getNeedses() {
        return this.needses;
    }
    
    public void setNeedses(Set needses) {
        this.needses = needses;
    }
    public Set getMatcheses() {
        return this.matcheses;
    }
    
    public void setMatcheses(Set matcheses) {
        this.matcheses = matcheses;
    }
    public Set getJobreportses() {
        return this.jobreportses;
    }
    
    public void setJobreportses(Set jobreportses) {
        this.jobreportses = jobreportses;
    }


    public String allJson(){
        StringBuilder json = new StringBuilder();
        json.append("{\n\"jobId\": \""+jobId+"\",\n");
        json.append("\"recruiter\": "+recruiter.allJson()+",\n");
        json.append("\"title\": \""+title+"\",\n");
        json.append("\"description\": \""+description+"\"\n}");
        return json.toString();
    }

     @Override
    public String toString() {
        return "Job{" + "jobId=" + jobId + ", recruiter=" + recruiter + ", title=" + title + ", description=" + description + ", visibility=" + visibility + ", createDate=" + createDate + ", applieses=" + applieses + ", needses=" + needses + ", matcheses=" + matcheses + ", jobreportses=" + jobreportses + '}';
    }
}


