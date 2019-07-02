package com.fine.websocket.service;

import com.fine.websocket.domain.Message;
import com.google.gson.Gson;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    //在线列表
    private static List list = new ArrayList();

    //路由表,sessionid和用户名绑定
    private static Map<String, String> routetab = new HashMap<>();

    //发送消息
    public void send(String content) {
        this.session.getAsyncRemote().sendText(content);
    }
    //广播
    public  void broadcast(String message){
        for (Websocket websocket : websockets) {
            websocket.session.getAsyncRemote().sendText(message);
        }
    }

    //返回给前端的消息
    public String info(List list){
        JSONObject member = new JSONObject();
        try {
            member.put("list",list);
        } catch (JSONException e) {

        }
        return member.toString();
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) {
        this.session = session;
        this.username = username;
        websockets.add(this);
        list.add(username);
        //routetab.put(session.getId(),username);
        String content = username + "进入了聊天室，当前人数："+ websockets.size();
//        Message message = new Message();
//        message.setContent(content);
//        broadcast(message.toJson());
//        broadcast(""+list);
        broadcast(content);
        broadcast(info(list));
    }

    @OnClose
    public void onClose() {
        websockets.remove(this);
        list.remove(username);
        routetab.remove(session.getId());
        String content = username + "离开了聊天室，当前人数："+ websockets.size();
//        Message message = new Message();
//        message.setContent(content);
//        broadcast(message.toJson());
//        broadcast(""+list);
        broadcast(content);
        broadcast(info(list));
    }

    @OnMessage
    public void onMessage(String content) {
        broadcast(username+content);
    }
//    private static Gson gson = new Gson();
//
//    /**前台传json格式数据
//     * 消息类型
//     * 发送人，接收人
//     * 消息时间
//     */
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
