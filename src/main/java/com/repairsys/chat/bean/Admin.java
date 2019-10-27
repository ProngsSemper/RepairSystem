package com.repairsys.chat.bean;

import java.util.LinkedList;
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

}
