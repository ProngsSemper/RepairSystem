package com.repairsys.chat.service;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.dao.MsgDao;
import com.repairsys.chat.domain.Message;

import java.util.List;


/**
 * @Author lyr
 * @create 2019/11/9 19:30
 */
public class MessageServiceImpl implements MsgService {
    private MessageServiceImpl(){}
    private static final MessageServiceImpl messageService = new MessageServiceImpl();
    public static MessageServiceImpl getInstance(){return messageService;}
    private  final MsgDao msgDao =  MsgDao.getInstance();
    /**
     * 添加一条记录到数据库
     *
     * @param jsonObject 聊天发送的 json字符串
     */
    @Override
    public void saveAdminMessage(JSONObject jsonObject) {
        if(jsonObject==null)
        {
            return;
        }
        msgDao.saveAdminMsg(
                jsonObject.getString("sender"),
                jsonObject.getString("target"),
                jsonObject.getString("msg"),
                0
        );
    }

    /**
     * 添加一条记录到 studentMsg 表里面
     *
     * @param jsonObject 聊天保存的离线 json字符串
     */
    @Override
    public void saveMessage(JSONObject jsonObject) {
        if(jsonObject==null)
        {
            return;
        }
        msgDao.saveStudentMsg(
                jsonObject.getString("sender"),
                jsonObject.getString("target"),
                jsonObject.getString("msg"),
                0
        );
    }

    public List<Message> getAdminPage(JSONObject jsonObject)
    {
        return msgDao.getAdminMsg(jsonObject.getString("target"),jsonObject.getInteger("page"),
                jsonObject.getInteger("size"));
    }

    public List<Message> getStudentPage(JSONObject jsonObject)
    {
        return msgDao.getStudentMsg(
                jsonObject.getString("target"),
                jsonObject.getInteger("page"),
                jsonObject.getInteger("size")
        );
    }


}
