package com.imatchprofile.model.pojo;
// Generated 27 d�c. 2017 17:10:07 by Hibernate Tools 4.3.1


import java.util.HashSet;
import java.util.Set;
import org.json.JSONObject;

/**
 * Skill generated by hbm2java
 */
public class Skill  implements java.io.Serializable {


     private Integer skillId;
     private String title;
     private Set needses = new HashSet(0);
     private Set masterses = new HashSet(0);

    public Skill() {
    }

	
    public Skill(String title) {
        this.title = title;
    }
    public Skill(String title, Set needses, Set masterses) {
       this.title = title;
       this.needses = needses;
       this.masterses = masterses;
    }
   
    public Integer getSkillId() {
        return this.skillId;
    }
    
    public void setSkillId(Integer skillId) {
        this.skillId = skillId;
    }
    public String getTitle() {
        return this.title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    public Set getNeedses() {
        return this.needses;
    }
    
    public void setNeedses(Set needses) {
        this.needses = needses;
    }
    public Set getMasterses() {
        return this.masterses;
    }
    
    public void setMasterses(Set masterses) {
        this.masterses = masterses;
    }

    public JSONObject toJSON() {
        JSONObject result = new JSONObject();
        result.put("skill_id", getSkillId());
        result.put("title", getTitle());
        return result;
    }


}


