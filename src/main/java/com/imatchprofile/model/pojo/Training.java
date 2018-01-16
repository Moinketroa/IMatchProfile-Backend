package com.imatchprofile.model.pojo;
// Generated 27 d�c. 2017 17:10:07 by Hibernate Tools 4.3.1


import java.util.Date;
import org.json.JSONObject;

/**
 * Training generated by hbm2java
 */
public class Training  implements java.io.Serializable {


     private Integer trainingId;
     private Candidate candidate;
     private String title;
     private String description;
     private Date startDate;
     private Date endDate;
     private String institute;

    public Training() {
    }

    public Training(Candidate candidate, String title, String description, Date startDate, Date endDate, String institute) {
       this.candidate = candidate;
       this.title = title;
       this.description = description;
       this.startDate = startDate;
       this.endDate = endDate;
       this.institute = institute;
    }
   
    public Integer getTrainingId() {
        return this.trainingId;
    }
    
    public void setTrainingId(Integer trainingId) {
        this.trainingId = trainingId;
    }
    public Candidate getCandidate() {
        return this.candidate;
    }
    
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
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
    public Date getStartDate() {
        return this.startDate;
    }
    
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return this.endDate;
    }
    
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getInstitute() {
        return this.institute;
    }
    
    public void setInstitute(String institute) {
        this.institute = institute;
    }

    public String toJSON() {
        JSONObject trainingJSON = new JSONObject();
        trainingJSON.put("training_id", trainingId);
        trainingJSON.put("title", title);
        trainingJSON.put("description", description);
        trainingJSON.put("startDate", startDate);
        trainingJSON.put("endDate", endDate);
        trainingJSON.put("institute", institute);
        
        return trainingJSON.toString();
    }


}


