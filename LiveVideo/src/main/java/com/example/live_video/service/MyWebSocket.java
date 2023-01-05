package com.example.live_video.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{id}")
public class MyWebSocket {

    //会话控制,与某个客户端的连接会话
    private Session session;

    //创建一个websocket容器,用于存放各个客户端的websocket.session

    private static ConcurrentHashMap<String, CopyOnWriteArraySet<MyWebSocket>> webSocketMap = new ConcurrentHashMap<>();

    //当有客户前端打开连接时, 把session存入websocket的集合容器中
    @OnOpen
    public void onOpen(@PathParam(value = "id") String id, Session session) {
        System.out.println(id);
        this.session = session;
        CopyOnWriteArraySet<MyWebSocket> webSocketSet = webSocketMap.get(session.getPathParameters().toString());
        System.out.println("open " + session.getPathParameters().toString());
        if (webSocketSet != null) {
            webSocketSet.add(this);
        } else {
            webSocketSet = new CopyOnWriteArraySet<>();
            webSocketSet.add(this);
            webSocketMap.put(session.getPathParameters().toString(), webSocketSet);
        }
        System.out.println("[webSocket] new connector,total:" + webSocketSet.size());

    }

    @OnClose
    public void onClose() {
        CopyOnWriteArraySet<MyWebSocket> webSocketSet = webSocketMap.get(this.session.getPathParameters().toString());
        System.out.println("close " + session.getPathParameters().toString());
        webSocketSet.remove(this);
        System.out.println("[webSocket] disconnect,total:" + webSocketSet.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("[webSocket] new message:" + message);
        System.out.println("new message " + session.getPathParameters().toString());
        sendMessage(message, webSocketMap.get(session.getPathParameters().toString()));
    }

    //定义发送广播消息的方法
    public void sendMessage(String message, CopyOnWriteArraySet<MyWebSocket> webSocketSet) {
        for (MyWebSocket websocket : webSocketSet) {
            try {
                websocket.session.getBasicRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}



