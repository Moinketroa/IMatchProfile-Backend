package com.imatchprofile.imatchprofile.model.pojo;
// Generated 8 d�c. 2017 20:48:06 by Hibernate Tools 4.3.1


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Message generated by hbm2java
 */
@Entity
@Table(name="message"
    ,catalog="imatchprofile"
)
public class Message  implements java.io.Serializable {


     private Integer messageId;
     private Chatroom chatroom;
     private byte sentByRecruiter;
     private String content;
     private Date sendingDate;

    public Message() {
    }

    public Message(Chatroom chatroom, byte sentByRecruiter, String content, Date sendingDate) {
       this.chatroom = chatroom;
       this.sentByRecruiter = sentByRecruiter;
       this.content = content;
       this.sendingDate = sendingDate;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="message_id", unique=true, nullable=false)
    public Integer getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="chatroom_id", nullable=false)
    public Chatroom getChatroom() {
        return this.chatroom;
    }
    
    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }

    
    @Column(name="sent_by_recruiter", nullable=false)
    public byte getSentByRecruiter() {
        return this.sentByRecruiter;
    }
    
    public void setSentByRecruiter(byte sentByRecruiter) {
        this.sentByRecruiter = sentByRecruiter;
    }

    
    @Column(name="content", nullable=false, length=300)
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="sending_date", nullable=false, length=19)
    public Date getSendingDate() {
        return this.sendingDate;
    }
    
    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }




}


