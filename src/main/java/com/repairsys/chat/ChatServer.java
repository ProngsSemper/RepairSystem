package com.repairsys.chat;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/10/26 14:35
 */
@ServerEndpoint(value="/chat",configurator=GetHttpSessionConfigurator.class)
public class ChatServer {

    @OnOpen
    public void onOpen(Session session, EndpointConfig config)
    {
        //TODO: 可以获取 httpsession了
        HttpSession httpSession = (HttpSession)config.getUserProperties().get(HttpSession.class.getName());

        System.out.println("通道已经连接");
    }


    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException {
        System.out.println("客户端说：" + message);


        while(true){
            session.getBasicRemote().sendText("world");
            Thread.sleep(2000);
        }
    }










}
