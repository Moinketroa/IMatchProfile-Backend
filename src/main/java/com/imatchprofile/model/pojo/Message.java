package com.imatchprofile.model.pojo;
// Generated 27 d�c. 2017 17:10:07 by Hibernate Tools 4.3.1


import java.util.Date;

/**
 * Message generated by hbm2java
 */
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
   
    public Integer getMessageId() {
        return this.messageId;
    }
    
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Chatroom getChatroom() {
        return this.chatroom;
    }
    
    public void setChatroom(Chatroom chatroom) {
        this.chatroom = chatroom;
    }
    public byte getSentByRecruiter() {
        return this.sentByRecruiter;
    }
    
    public void setSentByRecruiter(byte sentByRecruiter) {
        this.sentByRecruiter = sentByRecruiter;
    }
    public String getContent() {
        return this.content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    public Date getSendingDate() {
        return this.sendingDate;
    }
    
    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }




}


