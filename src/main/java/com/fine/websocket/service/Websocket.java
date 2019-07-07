package com.fine.websocket.service;

import com.fine.websocket.domain.Message;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

//请求路径
@ServerEndpoint("/websocket/{username}")
//注册到spring容器中
@Component
public class Websocket {
    //当前连接
    private Session session;
    private String username;

    //线程安全的无序集合,用一个集合保存当前所有连接
    private static CopyOnWriteArraySet<Websocket> websockets = new CopyOnWriteArraySet<>();

    //在线用户列表,列表必须绑定用户名和会话
    private static Map<String, Session> userList = new HashMap<>();

    //对指定session发送消息，即对指定用户发送消息
    public void send(String content,Session session) {
        session.getAsyncRemote().sendText(content);
    }

    //广播
    public  void broadcast(String message) throws IOException {
        for (Websocket websocket : websockets) {
            synchronized (session) {
                websocket.session.getBasicRemote().sendText(message);
            }
        }
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        this.session = session;
        this.username = username;
        websockets.add(this);
        userList.put(username, session);
        String content = username + "进入了聊天室，当前人数："+ websockets.size();
        Message message=new Message();
        message.setSender("系统消息：");
        message.setContent(content);
        broadcast(message.toJson());
        broadcast(userList.toString());
    }

    @OnClose
    public void onClose() throws IOException {
        websockets.remove(this);
        userList.remove(username);
        Message message=new Message();
        String content = username + "离开了聊天室，当前人数："+ websockets.size();
        message.setContent(content);
        message.setSender("系统消息：");
        broadcast(message.toJson());
        broadcast(userList.toString());
    }

    @OnMessage
    public void onMessage(String content) throws IOException {
        Message message = new Message();
        message.setContent(content);
        message.setSender(username);

        broadcast(message.toJson());
        broadcast(userList.toString());
  }

    @OnError
    public void onError(Session session, Throwable error){
        error.printStackTrace();
    }


}
