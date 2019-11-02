package com.repairsys.chat;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.vo.Result;
import com.repairsys.chat.bean.Admin;
import com.repairsys.chat.bean.User;
import com.repairsys.code.ResultEnum;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
//todo:本聊天室已经完成了单聊功能，但是前段页面还需完善

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
            if(onlineCount==1)
            {
                try {
                    session.getBasicRemote().sendText(User.getMsgString("管理员已经下线,请过段时间再来","聊天小助手",tmp));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    if(session.isOpen())
                    {
                        session.close();
                    }
                } catch (IOException e) {
                    logger.info("无管理员在线，关闭socket");
                }
                return;
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
            //TODO: 需要设置一个枚举类型，用来给前端反馈聊天类型
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

        logger.info("有新连接加入！当前在线人数为" + onlineCount);
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
        logger.info("聊天消息：{}", message);
        String path = Thread.currentThread().getContextClassLoader().getResource("").toString();
        path = path.replace('/', '\\');
        path = path.replace("file:", "");
        path = path.replace("classes\\", "");
        path = path.substring(1);
        logger.debug("敏感词路径：" + path);
        SensitiveWordFilter filter = new SensitiveWordFilter(path);
        //检测是否含有敏感词
        boolean isBadWords = filter.isContainSensitiveWord(message, 1);
        if (isBadWords) {
            message = filter.replaceSensitiveWord(message, 1, "*");
        }

        JSONObject jsonObject = JSONObject.parseObject(message);
        //TODO:需要设置一个枚举类型，返回给前端，前端判断类型来展示页面

        // send(jsonObject, session);
        //    todo:已经完成了单聊功能，但是目前先拿群聊代替，后期改回
        broadCast(jsonObject);
//        }

    }

    @OnClose
    public synchronized void onClose(Session session) {
        if(session.isOpen())
        {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        --onlineCount;
        if (isAdmin) {
            Admin admin = (Admin) ADMIN_MAP.get(this.userName);
            admin.broadCast(MAP);
            ADMIN_MAP.remove(userName);
        } else {
            if(this.userName==null)
            {
                return;
            }
            if(!MAP.contains(userName))
            {
                return;
            }
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
            logger.debug("下线了");
            session.getBasicRemote().sendText("{'tips':'对不起，对方已经下线'}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void broadCast(JSONObject jsonObject) {
        if (isAdmin) {
            for (Map.Entry<String, User> entry : MAP.entrySet()) {
                jsonObject.put("sender", "管理员 --" + this.userName);
                entry.getValue().receive(jsonObject);
            }
        } else {
            jsonObject.put("sender", this.userName);

            ADMIN_MAP.get(this.target).receive(jsonObject);

        }

    }

    @OnError
    public void onError(Throwable e,Session session)
    {
        logger.info("检测到退出连接，关闭连接");
        try {
            if(session.isOpen())
            {
                session.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


}
