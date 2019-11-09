package com.repairsys.chat.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Author lyr
 * @create 2019/11/4 2:32
 *
 * 事务处理类，专门用于封装一下奇奇怪怪的代码
 *
 */
public class ServerHandler {
    private static final ServerHandler SERVER = new ServerHandler();

    private ServerHandler(){}
    public static ServerHandler getInstance(){return SERVER;}


    private final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    /**
     * 存放离线消息
     */
    private  final LinkedBlockingQueue<JSONObject> MSG_QUEUE = new LinkedBlockingQueue<>();

    /**
     * 存放管理员离线消息
     */
    private  final LinkedBlockingQueue<JSONObject> ADMIN_MSG_QUEUE = new LinkedBlockingQueue<>();


    public void msgEnqueue(JSONObject jsonObject)
    {
        logger.info("学生消息入队 {}",jsonObject);
        MSG_QUEUE.offer(jsonObject);
    }

    public void adminMessageEnqueue(JSONObject jsonObject)
    {
        logger.info("管理员消息入队{}",jsonObject);
        ADMIN_MSG_QUEUE.offer(jsonObject);
    }









}
