package com.repairsys.bean.entity;

import java.io.Serializable;

/**
 * 学生对工人的详细（文字）评价
 *
 * @author Prongs
 * @date 2019/10/23 19:35
 */
public class Evaluation implements Serializable {
    private int id;
    private String msg;
    private int wKey;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getwKey() {
        return wKey;
    }

    public void setwKey(int wKey) {
        this.wKey = wKey;
    }

    @Override
    public String toString() {
        return "Evaluation{" +
                "id=" + id +
                ", msg='" + msg + '\'' +
                ", wKey=" + wKey +
                '}';
    }
}
