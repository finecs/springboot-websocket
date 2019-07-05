package com.fine.websocket.domain;

import com.google.gson.Gson;

import java.util.List;

/**
 * 用户消息实体类
 *
 * content消息内容
 */
public class Message {

    private int type;
    private String sender;
    private List<String> receiver;
    private String content;
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List<String> receiver) {
        this.receiver = receiver;
    }

    public Message() {
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

    private static Gson gson=new Gson();
}