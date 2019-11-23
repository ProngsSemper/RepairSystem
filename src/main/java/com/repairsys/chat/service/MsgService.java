package com.repairsys.chat.service;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author lyr
 * @create 2019/11/9 19:31
 */
public interface MsgService {

    /**
     * 添加一条记录到数据库
     *
     * @param jsonObject 聊天发送的 json字符串
     */
    void saveAdminMessage(JSONObject jsonObject);

    /**
     * 添加一条记录到 studentMsg 表里面
     *
     * @param jsonObject 聊天保存的离线 json字符串
     */
    void saveMessage(JSONObject jsonObject);

}
