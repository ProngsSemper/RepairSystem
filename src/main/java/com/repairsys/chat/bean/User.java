package com.repairsys.chat.bean;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Author lyr
 * @create 2019/10/27 11:01
 */
public class User implements Serializable {
    private Session session;
    private String userName;


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void receive(String msg)
    {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void receive(JSONObject jsonObject)
    {
        String text = jsonObject.toJSONString();
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
