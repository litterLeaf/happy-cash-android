package com.yinshan.happycash.dao;

import org.greenrobot.greendao.annotation.*;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit.
/**
 * Entity mapped to table "MESSAGE".
 */
@Entity
public class Message {

    @Id(autoincrement = true)
    private Long id;
    private Integer messageId;
    private Integer type;
    private Integer threadId;
    private String address;
    private String date;
    private String date_sent;
    private String subject;
    private String body;
    private Integer state;
    private Integer flag;

    @Generated
    public Message() {
    }

    public Message(Long id) {
        this.id = id;
    }

    @Generated
    public Message(Long id, Integer messageId, Integer type, Integer threadId, String address, String date, String date_sent, String subject, String body, Integer state, Integer flag) {
        this.id = id;
        this.messageId = messageId;
        this.type = type;
        this.threadId = threadId;
        this.address = address;
        this.date = date;
        this.date_sent = date_sent;
        this.subject = subject;
        this.body = body;
        this.state = state;
        this.flag = flag;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getThreadId() {
        return threadId;
    }

    public void setThreadId(Integer threadId) {
        this.threadId = threadId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate_sent() {
        return date_sent;
    }

    public void setDate_sent(String date_sent) {
        this.date_sent = date_sent;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

}
