package com.repairsys.chat.util;

import com.alibaba.fastjson.JSONObject;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @Author lyr
 * @create 2019/11/4 2:32
 *
 * 事务处理类，专门用于封装一下奇奇怪怪的代码
 *
 */
public class ServerHandler {

    /**
     * 存放离线消息
     */
    private static final ConcurrentLinkedQueue<JSONObject> MSG_QUEUE = new ConcurrentLinkedQueue<>();


    private static final ConcurrentLinkedQueue<JSONObject> ADMIN_MSG_QUEUE = new ConcurrentLinkedQueue<>();


    public void msgEnqueue(JSONObject jsonObject)
    {
        MSG_QUEUE.offer(jsonObject);
    }

    public void adminMessageEnqueue(JSONObject jsonObject)
    {
        ADMIN_MSG_QUEUE.offer(jsonObject);
    }









}
