package com.repairsys.chat.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @Author lyr
 * @create 2019/11/4 1:52
 */
public final class MsgSender {


    /**
     * @param arr 属性的 key 和 value ,偶数为 key,奇数为 value
     * @return
     */
    public static String jsonText(Object... arr)
    {
        //只是一个工具类而已，规定 arr的长度必须是偶数，创建一个 json字符串,偶数为 key,奇数为 value
        if((arr.length&1)==1)
        {
            return "";
        }
        JSONObject jsonObject = new JSONObject();

        for(int i=0;i<arr.length;i+=2)
        {
            jsonObject.put((String) arr[i],arr[i+1]);
        }
        System.out.println(jsonObject.toJSONString());


        return jsonObject.toJSONString();
    }

    public static MessagePack jsonString()
    {
        return new MessagePack();
    }


    // public static JSONObject.

    public static class MessagePack
    {
        JSONObject jsonObject = new JSONObject();

        public MessagePack() {
        }
        public MessagePack add(String k,Object v)
        {
            jsonObject.put(k,v);
            return this;
        }

        @Override
        public String toString() {
            return this.jsonObject.toJSONString();
        }
    }






}
