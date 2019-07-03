package com.fine.websocket.service;

import com.fine.websocket.domain.Message;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

//请求路径
@ServerEndpoint("/websocket")
//注册到spring容器中
@Component
public class Websocket {
    //当前连接
    private Session session;
    private String username;

    //线程安全的无序集合,用一个集合保存当前所有连接
    private static CopyOnWriteArraySet<Websocket> websockets = new CopyOnWriteArraySet<>();

    //在线列表,列表必须绑定用户名和会话，前端点击就能进入私聊会话

    //路由表,sessionid和用户名绑定，路由表即时会话列表
    private static Map<String, String> routetab = new HashMap<>();

    //对指定session发送消息，即对指定用户发送消息
    public void send(String content,Session session) {
        session.getAsyncRemote().sendText(content);
    }

    //广播
    public  void broadcast(String message){
        for (Websocket websocket : websockets) {
            websocket.session.getAsyncRemote().sendText(message);
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        websockets.add(this);
        routetab.put(session.getId(),username);
        String content = username + "进入了聊天室，当前人数："+ websockets.size();
        broadcast(content);
        broadcast(routetab.toString());
    }

    @OnClose
    public void onClose() {
        websockets.remove(this);
        routetab.remove(session.getId());
        String content = username + "离开了聊天室，当前人数："+ websockets.size();
        broadcast(content);
        broadcast(routetab.toString());
    }

    @OnMessage
    public void onMessage(String content) {
        broadcast(username+content);
    }
//    @OnMessage
//    public void onMessage(String json){
//        Message message = gson.fromJson(json, Message.class);
//        if (message.getType() ==1) {
//            Message groupMessage = new Message();
//            groupMessage.setType(message.getType());
//            groupMessage.setNames(map);
//            groupMessage.setContent(message.getContent());
//            broadcast(groupMessage.toJson());
//        }else {
//            Message privateMessage = new Message();
//            privateMessage.setType(message.getType());
//            privateMessage.setNames(map);
//            privateMessage.setContent(message.getContent());
//
//        }
//    }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }


}
