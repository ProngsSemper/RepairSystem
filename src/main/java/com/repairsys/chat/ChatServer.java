package com.repairsys.chat;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.domain.Admin;
import com.repairsys.chat.domain.User;
import com.repairsys.chat.util.MsgSender;
import com.repairsys.chat.util.ServerHandler;
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
    private static final String OFFLINE_MSG = "离线留言";
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
    private static final ServerHandler SERVER_HANDLER = ServerHandler.getInstance();




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
        logger.debug(name);
        logger.debug(name);

        if (name == null) {
            //学生
            String tmp = (String) httpSession.getAttribute("stuId");
            session.getBasicRemote().sendText(MsgSender.jsonString()
                    .add("type",ChatEnum.SELF_INFO.getCode())
                    .add("sender",tmp)
                    .add("isAdmin",false)
                    .toString()


            );

            if(ADMIN_MAP.isEmpty())
            {
                /*
                *
                * 如果学生登录，并且管理员不在线，要反馈给学生
                *
                * */

                session.getBasicRemote().sendText(MsgSender.jsonString()
                        .add("sender","聊天小助手")
                        .add("msg","对不起，目前没有管理员在线哦！有时请留言")
                        .add("type",ChatEnum.TALK.getCode())
                        .toString());


            }
            //将学生的session ，联系方式，用户名字保存起来

            User u = MAP.getOrDefault(tmp, new User());
            u.setSession(session);
            u.setUserName(tmp);
            //登记聊天信息到后台
            MAP.put(tmp, u);


            this.userName = tmp;
            logger.info("用户名："+this.userName);

            MsgSender.MessagePack pack = MsgSender.jsonString()
                    .add("type",ChatEnum.UPDATE_LIST.getCode())
                    .add("onlineList",ADMIN_MAP.keySet());

            session.getBasicRemote().sendText(

                    pack.toString()

            );
            logger.warn("学生登录");
            //todo: 广播给管理员
            pack.add("onlineList",MAP.keySet()).add("type",ChatEnum.UPDATE_LIST.getCode());

            MsgSender.broadCast(ADMIN_MAP,pack.toString());


        } else {
            //管理员 处理
            this.userName = name;
            User u = ADMIN_MAP.getOrDefault(name, new Admin());
            u.setSession(session);
            u.setUserName(name);

            this.isAdmin = true;
            ADMIN_MAP.put(name, u);
            System.out.println(MAP.keySet());
            MsgSender.MessagePack pack =
                    MsgSender.jsonString()
                    .add("type",ChatEnum.SELF_INFO.getCode())
                    .add("sender",name)
                    .add("isAdmin",true)
                    .add("onlineList",MAP.keySet());
            //将用户的信息回复给前端
            session.getBasicRemote().sendText(

                    pack.toString()
            );
            logger.warn("管理员登录");
            logger.error("学生聊天集合{}",MAP.keySet());
            //todo:广播给学生
            pack.add("onlineList",ADMIN_MAP.keySet()).add("type",ChatEnum.UPDATE_LIST.getCode());

            MsgSender.broadCast(MAP,pack.toString());


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
            logger.debug("心跳检测");
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
                logger.info("发送成功");

                break;

            }

            default:{
                logger.info("出现 default事务");
            }
        }


        logger.info("网络消息：{}", message);







    }

    @OnClose
    public synchronized void onClose(Session session) {
        logger.debug("--- close ---");

        --onlineCount;
        if (isAdmin) {
            Admin admin = (Admin) ADMIN_MAP.get(this.userName);
            ADMIN_MAP.remove(userName);
            String jsonText = MsgSender.jsonText("聊天小助手",this.userName+" 管理员下线了","type",ChatEnum.OFFLINE.getCode(),"onlineList",ADMIN_MAP.keySet());
            MsgSender.broadCast(MAP,jsonText);
        } else {
            if(this.userName==null)
            {
                return;
            }

            User u = MAP.get(userName);



            MAP.remove(userName);
            String jsonText = MsgSender.jsonString()
                    .add("type",ChatEnum.UPDATE_LIST.getCode())
                    .add("onlineList",MAP.keySet())
                    .toString();
            MsgSender.broadCast(ADMIN_MAP,jsonText);



        }
        if(session.isOpen())
        {
            try {
                session.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        logger.info("当前在线人数: {}",onlineCount);

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
        logger.info("检测到异常连接，关闭连接");
        try {
            if(session.isOpen())
            {
                session.close();


            }
            logger.info("当前在线人数:{}",onlineCount);
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            if(isAdmin)
            {
                ADMIN_MAP.remove(this.userName);
            }else{
                MAP.remove(this.userName);

            }

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

    /**
     * 专门用来处理聊天事务的方法
     * @param jsonObject 前端发来的 json聊天数据
     */
    private void chatHandler(JSONObject jsonObject)
    {
        String target = jsonObject.getString("target");

        //
        boolean b =target==null||target.length()<2 ;
        if(b)
        {
            logger.debug("空消息-直接返回");
            return;
        }

        String completedMsg = filter(jsonObject.getString("msg"));
        jsonObject.put("msg",completedMsg);
        
        if(isAdmin)
        {
            /*
            * 如果是留言消息，或者对方已经下线，写入数据库
            *
            * */
            if(MAP.isEmpty()|| OFFLINE_MSG.equals(target)){
                //todo:管理员消息入队
                SERVER_HANDLER.adminMessageEnqueue(jsonObject);

            }
            else if(ALL.equals(target))
            {
                broadCast(jsonObject);
            }else{
                if(MAP.containsKey(target))
                {
                    MAP.get(target).receive(jsonObject);
                }else{
                    /*
                    * 如果 map 中没有这个人，也写入数据库里面
                    *
                    * */
                    SERVER_HANDLER.adminMessageEnqueue(jsonObject);
                }

            }
        }else{

            if(OFFLINE_MSG.equals(target))
            {
                /*
                *
                * 如果是离线留言，写入数据库
                * */
                SERVER_HANDLER.msgEnqueue(jsonObject);
            }else if(ADMIN_MAP.containsKey(target))
            {
                logger.info("发送消息");
                ADMIN_MAP.get(target).receive(jsonObject);

            }else{
                /*
                 *
                 * 如果map中，没有这个对象，写入数据库里面
                 *
                 * */
                SERVER_HANDLER.msgEnqueue(jsonObject);
            }


        }

    }


}
