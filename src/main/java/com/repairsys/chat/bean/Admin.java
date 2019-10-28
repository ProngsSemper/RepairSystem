package com.repairsys.chat.bean;

import java.util.LinkedList;
import java.util.Map;
import java.util.UUID;

/**
 * @Author lyr
 * @create 2019/10/27 12:58
 */
public class Admin extends User {
    public LinkedList<String> getTargetSet() {
        return targetSet;
    }
    public void append(String name)
    {
        targetSet.add(name);
    }
    public void remove(String name)
    {
        targetSet.remove(name);
    }

    private LinkedList<String> targetSet = new LinkedList<>();


    public String getList()
    {
        return "{ 'list':"+this.targetSet+"}";
    }
    public void broadCast(Map<String,User> map)
    {
        for(String t: targetSet)
        {
            User tmp = map.get(t);
            if(tmp!=null)
            {
                tmp.receive("{'msg': '管理员下线了'}");
            }

        }

    }

}
