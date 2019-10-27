package com.repairsys.bean.entity;

import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import java.io.Serializable;

/**
 * @Author lyr
 * @create 2019/10/27 11:01
 */
public class User implements Serializable {
    Session session;
    String userName;


}
