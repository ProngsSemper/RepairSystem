package com.repairsys.chat.domain;

import com.alibaba.fastjson.JSONObject;

import java.util.LinkedList;
import java.util.Map;

/**
 * @Author lyr
 * @create 2019/10/27 12:58
 */
public class Admin extends User {
    // public LinkedList<String> getTargetSet() {
    //     return targetSet;
    // }
    //
    // public void append(String name) {
    //     targetSet.add(name);
    // }
    //
    // public void remove(String name) {
    //     targetSet.remove(name);
    // }

    // private LinkedList<String> targetSet = new LinkedList<>();


    // @Deprecated
    // public String getList() {
    //     return "{ 'list':" + this.targetSet + "}";
    // }

    /**
     * @param map
     * @deprecated
     */
    @Deprecated
    public void broadCast(Map<String, User> map) {

        // for (String t : targetSet) {
        //     User tmp = map.get(t);
        //     if (tmp != null) {
        //         tmp.receive("{'msg': '管理员下线了'}");
        //     }
        //
        // }

    }

    /**
     * @param map 聊天对象集合
     * @param jsonText 发送的json 数据
     * @deprecated 使用工具类 MsgSender 提供的broadCast 方法
     */
    public void broadCast(Map<String,User> map, String jsonText)
    {
        // for (String t : targetSet) {
        //     User tmp = map.get(t);
        //     if (tmp != null) {
        //         tmp.receive(jsonText);
        //     }
        //
        // }

    }


}
