package com.repairsys.chat;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.bean.Admin;
import com.repairsys.chat.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author lyr
 * @create 2019/10/26 14:35
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatServer {
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    private static int onlineCount = 0;
    private static final ConcurrentHashMap<String, User> MAP = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<String, User> ADMIN_MAP = new ConcurrentHashMap<>();
    private static final Random R = new Random();
    private String userName;
    private String target;
    private boolean isAdmin = false;

    private String getTarget() {
        String target = null;
        int i = ADMIN_MAP.size() <= 0 ? 0 : R.nextInt(ADMIN_MAP.size());
        for (Map.Entry<String, User> t : ADMIN_MAP.entrySet()) {
            --i;
            if (i <= 0) {
                target = t.getKey();
                break;
            }
        }
        return target;
    }

    private User getPersonToTalk() {
        User target = null;
        int i = ADMIN_MAP.size() == 0 ? 0 : R.nextInt(ADMIN_MAP.size());
        for (Map.Entry<String, User> t : ADMIN_MAP.entrySet()) {
            --i;
            if (i <= 0) {
                target = t.getValue();
                break;
            }
        }
        this.target = target.getUserName();
        return target;
    }

    @OnOpen
    public synchronized void onOpen(Session session, EndpointConfig config) {

        ++onlineCount;
        //TODO: 可以获取 httpsession了
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String name = (String) httpSession.getAttribute("adminId");
        //todo: 测试阶段
        logger.info(name);
        logger.error(name);

        if (name == null) {
            //学生或者游客
            String tmp = (String) httpSession.getAttribute("stuId");
            if (tmp == null) {
                String y = UUID.randomUUID().toString().substring(0, 5);
                tmp = "游客" + y + onlineCount;
            }
            User u = MAP.getOrDefault(tmp, new User());
            u.setSession(session);
            u.setUserName(tmp);
            MAP.put(tmp, u);
            Admin admin = (Admin) getPersonToTalk();
            admin.append(tmp);
            this.userName = tmp;

            u.setTarget(this.target);

            String text = "{ 'list':" + admin.getTargetSet() + "}";
            admin.receive(text);
            u.receive("{'target':'" + admin.getUserName() + "'}");

        } else {
            this.userName = name;
            //管理员
            User u = ADMIN_MAP.getOrDefault(name, new Admin());
            u.setSession(session);
            u.setUserName(name);

            this.isAdmin = true;
            ADMIN_MAP.put(name, u);

        }

        System.out.println("有新连接加入！当前在线人数为" + onlineCount);
    }

    /**
     * 如果对方已经下线，就无法收到
     *
     * @param message 对方发送回来的json格式的数据， 要有 target 来和对方讲话，target是屏幕显示的账号,identity 是自己的身份，是admin还是不同的管理员
     * @param session 就是session
     * @throws IOException
     * @throws InterruptedException
     */
    @OnMessage
    public void onMessage(String message, Session session)
            throws IOException, InterruptedException {

        System.out.println("客户端说：" + message);
        JSONObject jsonObject = JSONObject.parseObject(message);
        send(jsonObject, session);

    }

    @OnClose
    public synchronized void onClose() {
        --onlineCount;
        if (isAdmin) {
            Admin admin = (Admin) ADMIN_MAP.get(this.userName);
            admin.broadCast(MAP);
            ADMIN_MAP.remove(userName);
        } else {
            User u = MAP.get(userName);

            Admin admin = (Admin) ADMIN_MAP.get(u.getTarget());
            admin.remove(userName);
            admin.receive(admin.getList());

            MAP.remove(userName);
        }

    }

    public void send(JSONObject jsonObject, Session session) throws IOException {
        String target = jsonObject.getString("target");

        if (!isAdmin) {
            //    如果没有 identity的标识，说明是游客或者学生
            //学生发送给管理员，需要查找管理员的''电话号码''
            System.out.println(3);

            User u = ADMIN_MAP.get(this.target);

            // boolean notNull = checkEmpty(u);
            if (u == null) {

                offlineCall(session);
                // return;
            } else {
                u.receive(jsonObject);
            }

        } else {
            System.out.println(4);
            //    如果有标记，说明是管理员,要对学生发信息
            User u = MAP.get(target);

            if (u != null) {
                u.receive(jsonObject);
                // return;
            } else {
                System.out.println(5);
                offlineCall(session);
            }
        }

    }

    public void offlineCall(Session session) {
        try {
            System.out.println("找不到");
            session.getBasicRemote().sendText("{'tips':'对不起，对方已经下线'}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // public boolean checkEmpty(String text)
    // {
    //     if(text==null||text.length()<=3)
    //     {
    //         System.out.println("找不到");
    //         return false;
    //     }
    //     return true;
    // }

    // public boolean checkEmpty(Object text)
    // {
    //     if(text==null)
    //     {
    //         System.out.println("找不到");
    //         return false;
    //     }
    //     System.out.println("找到了");
    //     return true;
    // }

}
