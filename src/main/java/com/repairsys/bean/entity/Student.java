package com.repairsys.bean.entity;

import java.io.Serializable;

/**
 * @Author lyr
 * @create 2019/9/24 12:11
 */
public class Student implements Serializable {
    String stuId;
    String stuName;
    String stuTel;
    String stuPassword;
    public Student(){}

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuTel() {
        return stuTel;
    }

    public void setStuTel(String stuTel) {
        this.stuTel = stuTel;
    }

    public String getStuPassword() {
        return stuPassword;
    }

    public void setStuPassword(String stuPassword) {
        this.stuPassword = stuPassword;
    }





}
