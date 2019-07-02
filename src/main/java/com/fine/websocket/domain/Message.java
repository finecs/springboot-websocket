package com.fine.websocket.domain;

import com.google.gson.Gson;

import java.util.Date;
import java.util.Map;

/**
 * 用户消息实体类
 * type判断消息是群聊消息，私聊消息
 * names存放当前session id和姓名
 * content消息内容
 * localDateTime消息发送时间
 */
public class Message {
    private int type;
    private String sender;
    private String receiver;
    private String content;
    private Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public Message() {
    }
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toJson(){
        return gson.toJson(this);
    }
    private static Gson gson = new Gson();

}