package com.repairsys.chat;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.domain.Admin;
import com.repairsys.chat.domain.User;
import com.repairsys.chat.util.MsgSender;
import com.repairsys.code.ChatEnum;
import com.repairsys.dao.DaoFactory;
import com.repairsys.util.textfilter.SensitiveWordFilter;
import com.repairsys.util.textfilter.TextFilterFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
//todo:本聊天室已经完成了单聊功能，但是前段页面还需完善

/**
 * @Author lyr
 * @create 2019/10/26 14:35
 */
@ServerEndpoint(value = "/chat", configurator = GetHttpSessionConfigurator.class)
public class ChatServer {
    private static final String ALL = "所有人";
    private static final Logger logger = LoggerFactory.getLogger(ChatServer.class);
    /**
     * 在线人数
     */
    private static int onlineCount = 0;
    /**
     * 在线学生
     */
    private static final ConcurrentHashMap<String, User> MAP = new ConcurrentHashMap<>();
    /**
     * 在线管理员
     */
    private static final ConcurrentHashMap<String, User> ADMIN_MAP = new ConcurrentHashMap<>();


    /**
     * 存放离线消息
     */
    private static final ConcurrentLinkedQueue MSG_QUEUE = new ConcurrentLinkedQueue();

    private static final Random R = new Random();



    private String userName;
    private String target;
    private boolean isAdmin = false;

    /**
     * @return target的名字
     * @deprecated 模式改为前端选择管理员聊天
     */
    @Deprecated
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

    /**
     * 为了方便，前端随机选择管理员进行聊天，不用后台随机选择
     * @deprecated 为了方便，让前端js随机选择管理员聊天
     * @return
     */
    @Deprecated
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
        if(target!=null)
        {
            this.target = target.getUserName();
        }
        return target;
    }

    @OnOpen
    public synchronized void onOpen(Session session, EndpointConfig config) throws IOException {
        




        ++onlineCount;
        //TODO: 可以获取 httpsession了
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        String name = (String) httpSession.getAttribute("adminId");
        //todo: 测试阶段
        logger.info(name);
        logger.error(name);

        if (name == null) {
            //学生
            String tmp = (String) httpSession.getAttribute("stuId");
            session.getBasicRemote().sendText(MsgSender.jsonString()
                    .add("type",ChatEnum.SELF_INFO.getCode())
                    .add("sender",tmp)
                    .add("isAdmin",false)
                    .toString()


            );

            if(onlineCount==1)
            {

                session.getBasicRemote().sendText(MsgSender.jsonString()
                        .add("sender","聊天小助手")
                        .add("msg","管理员已经下线了")
                        .add("type",ChatEnum.OFFLINE.getCode())
                        .toString());

                //todo: 如果发现管理员不在线的话，那就不能关闭 webSocket 了
                return;

            }
            //初始化代码
            //============================================================================================

            User u = MAP.getOrDefault(tmp, new User());
            u.setSession(session);
            u.setUserName(tmp);
            //登记聊天信息到后台
            MAP.put(tmp, u);
            //随机获取一名管理员聊天
            Admin admin = (Admin) getPersonToTalk();
            admin.append(tmp);
            admin.receive(MsgSender.jsonString()
                    .add("onlineList",admin.getTargetSet())
                    .add("type",ChatEnum.UPDATE_LIST.getCode())
                    .toString());

            //记住本连接的用户名
            this.userName = tmp;

            u.setTarget(this.target);
            //========================================================================================


            //TODO: 需要设置一个枚举类型，用来给前端反馈聊天类型


            session.getBasicRemote().sendText(
                    MsgSender.jsonString()
                    .add("type",ChatEnum.UPDATE_LIST.getCode())
                    .add("onlineList",ADMIN_MAP.keySet().toArray())
                    .toString()

            );
            logger.warn("学生登录");

        } else {
            this.userName = name;
            //管理员 处理
            User u = ADMIN_MAP.getOrDefault(name, new Admin());
            u.setSession(session);
            u.setUserName(name);

            this.isAdmin = true;
            ADMIN_MAP.put(name, u);
            //将用户的信息回复给前端
            session.getBasicRemote().sendText(
                    MsgSender.jsonString()
                    .add("type",ChatEnum.SELF_INFO.getCode())
                    .add("sender",name)
                    .add("isAdmin",true)
                    .add("onlineList",MAP.keySet().toArray())
                    .toString()
            );
            logger.warn("管理员登录");

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
        System.out.println(message);
        if(message.length()<=0)
        {
            logger.error("心跳检测");
            //普通的心跳检测不需要转 json，直接回复
            session.getBasicRemote().sendText(message);
            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(message);
        //TODO:需要设置一个枚举类型，返回给前端，前端判断类型来展示页面
        //todo: 发给直接
        Integer code = jsonObject.getInteger("type");
        // 根据code来判断响应的处理事务

        switch(ChatEnum.getByCode(code))
        {
            //心跳测试

            //聊天
            case TALK:
            {
                logger.debug("聊天事务{}",message);
                chatHandler(jsonObject);
                logger.error("发送成功");

                break;

            }
            //




            default:{
                logger.error("出现 default事务");
            }
        }


        logger.info("网络消息：{}", message);






        // send(jsonObject, session);
        //    todo:已经完成了单聊功能，但是目前先拿群聊代替，后期改回
        // broadCast(jsonObject);
//        }

    }

    @OnClose
    public synchronized void onClose(Session session) {
        System.out.println("--- close ---");
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
            String jsonText = MsgSender.jsonText("聊天小助手",this.userName+" 管理员下线了","type",ChatEnum.OFFLINE);
            admin.broadCast(MAP,jsonText);
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
            String jsonText = MsgSender.jsonString()
                    .add("type",ChatEnum.UPDATE_LIST.getCode())
                    .add("onlineList",admin.getTargetSet())
                    .toString();

            admin.receive(jsonText);

            MAP.remove(userName);
        }

    }

    /**
     * 根据 json中的 target，选择用户进行发送
     * @param jsonObject json数据
     * @param session 当前用户的 session
     * @throws IOException
     * @deprecated 内容扩展过少
     */
    @Deprecated
    public void send(JSONObject jsonObject, Session session) throws IOException {
        String target = jsonObject.getString("target");

        if (!isAdmin) {
            //    如果没有 identity的标识，说明是游客或者学生
            //学生发送给管理员，需要查找管理员的''电话号码''


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

                offlineCall(session);
            }
        }

    }

    /**
     * 下线提示
     * @param session 连接的session
     * @deprecated 内容扩展太少了
     */
    @Deprecated
    public void offlineCall(Session session) {
        try {
            logger.debug("下线了");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tips","对不起，对方已经下线");
            session.getBasicRemote().sendText(jsonObject.toJSONString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 广播聊天消息
     * @param jsonObject json的数据
     */
    private void broadCast(JSONObject jsonObject) {
        if (isAdmin) {
            jsonObject.put("sender", "管理员 --" + this.userName);
            for (Map.Entry<String, User> entry : MAP.entrySet()) {

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
        logger.error("检测到异常连接，关闭连接");
        try {
            if(session.isOpen())
            {
                session.close();

            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * 用来进行敏感词过滤
     * @param message 用户发来的 message消息
     * @return 返回处理后的字符串
     */
    private String filter(String message)
    {
        SensitiveWordFilter filter = TextFilterFactory.getInstance().getChatPathFilter();
        //检测是否含有敏感词
        boolean isBadWords = filter.isContainSensitiveWord(message, 1);
        if (isBadWords) {
            return filter.replaceSensitiveWord(message, 1, "*");

        }
        return message;
    }

    private void chatHandler(JSONObject jsonObject)
    {
        String target = jsonObject.getString("target");
        System.out.println(target);

        if(target==null)
        {
            return;
        }
        String completedMsg = filter(jsonObject.getString("msg"));
        jsonObject.put("msg",completedMsg);
        if(isAdmin)
        {
            //todo:暂时用广播代替，后期使用 sender来指定聊天对象
            if(ALL.equals(target))
            {
                broadCast(jsonObject);
            }else{
                if(MAP.containsKey(target))
                {
                    MAP.get(target).receive(jsonObject);
                }

            }
        }else{

            if(target!=null&& ADMIN_MAP.containsKey(target))
            {
                logger.error("发送消息");
                ADMIN_MAP.get(target).receive(jsonObject);

            }else{
                logger.error("消息队列");
                //todo: 消息队列
            }

        }

    }


}
