package com.repairsys.bean.entity;

import java.io.Serializable;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 12:11
 */
public class Admin implements Serializable {
    private String adminId;
    private String adminName;
    private String adminPassword;
    private String adminMail;
    private int adminKey;
    private String adminToken;

    public Admin() {
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminMail() {
        return adminMail;
    }

    public void setAdminMail(String adminMail) {
        this.adminMail = adminMail;
    }

    public int getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(int adminKey) {
        this.adminKey = adminKey;
    }

    public String getAdminToken() {
        return adminToken;
    }

    public void setAdminToken(String adminToken) {
        this.adminToken = adminToken;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminPassword='" + adminPassword + '\'' +
                ", adminMail='" + adminMail + '\'' +
                ", adminKey=" + adminKey +
                ", adminToken='" + adminToken + '\'' +
                '}';
    }
}
