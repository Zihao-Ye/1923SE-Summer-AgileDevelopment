package com.example.SESummer.Controller;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{userID}/{questionnaireID}")
//此注解相当于设置访问URL
public class WebSocketServer {

    private Session session;

    private static CopyOnWriteArraySet<WebSocketServer> webSockets =new CopyOnWriteArraySet<>();
    private static Map<String,Session> sessionPool = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam(value="userID")String userID) {
        this.session = session;
        webSockets.add(this);
        sessionPool.put(userID, session);
        System.out.println("[message]有新的连接，总数为:"+webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        System.out.println("[message]连接断开，总数为:"+webSockets.size());
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("[message]收到客户端消息:"+message);
    }

    // 广播消息
    public void sendAllMessage(String message) {
        for(WebSocketServer webSocket : webSockets) {
            System.out.println("[message]]广播消息:"+message);
            try {
                webSocket.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 广播发送对象
    public void sendAllObject(Object message){
        for (WebSocketServer webSocket : webSockets){
            try {
                webSocket.session.getAsyncRemote().sendObject(message);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    // 单点消息 (发送文本)
    public void sendTextMessage(String userID, String message) {
        Session session = sessionPool.get(userID);
        if (session != null) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // 单点消息 (发送对象)
    public void sendObjMessage(String userID, Object message){
        Session session = sessionPool.get(userID);
        if (session != null) {
            try {
                session.getAsyncRemote().sendObject(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}