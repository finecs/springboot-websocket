package com.fine.websocket.domain;

import java.util.List;

/**
 * 用户消息实体类
 *
 * content消息内容
 */
public class Message {
    private String sender;
    private List<String> receiver;
    private String content;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public List<String> getReceiver() {
        return receiver;
    }

    public void setReceiver(List receiver) {
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

}