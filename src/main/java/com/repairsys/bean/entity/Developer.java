package com.repairsys.bean.entity;

import java.io.Serializable;

/**
 * 开发这一套系统的学生需要预备不时之需
 *
 * @Author lyr
 * @create 2019/10/2 15:23
 */
public class Developer implements Serializable {
    private String id;
    private String password;
    private String stuName;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Developer{" +
                "id='" + id + '\'' +
                ", password='" + password + '\'' +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}
