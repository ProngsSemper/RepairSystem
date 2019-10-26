package com.repairsys.chat;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
/**
 * @Author lyr
 * @create 2019/10/26 14:35
 */
@ServerEndpoint("/chat")
public class ChatServer {

    @OnOpen
    public void onOpen(Session session)
    {
        System.out.println("通道已经连接");
    }


    @OnMessage
    public void onMessage(String message)
    {
        System.out.println("开始接受数据");
    }
    // @OnClose
    // public void onClose()
    // {
    //
    // }










}
