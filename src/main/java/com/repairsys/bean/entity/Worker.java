package com.repairsys.bean.entity;

import java.io.Serializable;

/**
 * @Author lyr
 * @create 2019/9/24 12:11
 */
public class Worker implements Serializable {
    private String wId;
    private String wName;
    private String wTel;
    private String wPassword;
    private String wMail;
    private String wKey;

    public Worker() {
    }

    public String getwMail() {
        return wMail;
    }

    public void setwMail(String wMail) {
        this.wMail = wMail;
    }

    public String getwKey() {
        return wKey;
    }

    public void setwKey(String wKey) {
        this.wKey = wKey;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public String getwTel() {
        return wTel;
    }

    public void setwTel(String wTel) {
        this.wTel = wTel;
    }

    public String getwPassword() {
        return wPassword;
    }

    public void setwPassword(String wPassword) {
        this.wPassword = wPassword;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "wId='" + wId + '\'' +
                ", wName='" + wName + '\'' +
                ", wTel='" + wTel + '\'' +
                ", wPassword='" + wPassword + '\'' +
                '}' + "\r\n";
    }
}
