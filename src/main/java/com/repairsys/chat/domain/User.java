package com.repairsys.chat.domain;

import com.alibaba.fastjson.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.io.Serializable;

/**
 * @Author lyr
 * @create 2019/10/27 11:01
 */
public class User implements Serializable {
    protected Session session;
    protected String userName;

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    private String target;

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

    public void receive(String msg) {
        try {
            this.session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receive(JSONObject jsonObject) {
        String text = jsonObject.toJSONString();
        try {
            this.session.getBasicRemote().sendText(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveJson(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        try {
            this.session.getBasicRemote().sendText(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getMsgString(String msg) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        return jsonObject.toJSONString();

    }

    public static String getMsgString(String msg, String sender, String target) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", msg);
        jsonObject.put("sender", sender);
        jsonObject.put("target", target);
        return jsonObject.toJSONString();

    }
}
