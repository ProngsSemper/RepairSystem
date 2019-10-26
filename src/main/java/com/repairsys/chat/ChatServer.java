package com.repairsys.chat;
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
