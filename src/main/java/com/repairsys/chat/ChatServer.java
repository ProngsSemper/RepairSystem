package com.repairsys.chat;
import com.repairsys.bean.entity.User;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lyr
 * @create 2019/10/26 14:35
 */
@ServerEndpoint(value="/chat",configurator=GetHttpSessionConfigurator.class)
public class ChatServer {
    private static int onlineCount = 0;
    private static final ConcurrentHashMap<String, User> MAP = new ConcurrentHashMap();
    private Session session;
    

    @OnOpen
    public void onOpen(Session session, EndpointConfig config)
    {
        //TODO: 可以获取 httpsession了
        HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());

    }


    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException {
        System.out.println("客户端说：" + message);



    }

    @OnClose
    public void onClose()
    {

    }










}
