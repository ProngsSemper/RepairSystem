package com.repairsys.bean.entity;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 12:11
 */
public class Form implements Serializable {
    private String stuId;
    private byte queryCode;
    private int formId;
    private String formMsg;
    private Date formDate;
    private String stuMail;
    private String photoId;
    private int adminKey;
    private int wKey;
    private Date endDate;
    private String room;
    private String stuName;
    private String stuPhone;
    private String wType;
    private int appointment;
    private String appointDate;

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Form() {
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public byte getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(byte queryCode) {
        this.queryCode = queryCode;
    }

    public int getFormId() {
        return formId;
    }

    public void setFormId(int formId) {
        this.formId = formId;
    }

    public String getFormMsg() {
        return formMsg;
    }

    public void setFormMsg(String formMsg) {
        this.formMsg = formMsg;
    }

    public Date getFormDate() {
        return formDate;
    }

    public void setFormDate(Date formDate) {
        this.formDate = formDate;
    }

    public String getStuMail() {
        return stuMail;
    }

    public void setStuMail(String stuMail) {
        this.stuMail = stuMail;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }

    public int getAdminKey() {
        return adminKey;
    }

    public void setAdminKey(int adminKey) {
        this.adminKey = adminKey;
    }

    public int getwKey() {
        return wKey;
    }

    public void setwKey(int wKey) {
        this.wKey = wKey;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getwType() {
        return wType;
    }

    public void setwType(String wType) {
        this.wType = wType;
    }

    public int getAppointment() {
        return appointment;
    }

    public void setAppointment(int appointment) {
        this.appointment = appointment;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    @Override
    public String toString() {
        return "Form{" +
                "stuId='" + stuId + '\'' +
                ", queryCode=" + queryCode +
                ", formId=" + formId +
                ", formMsg='" + formMsg + '\'' +
                ", formDate=" + formDate +
                ", stuMail='" + stuMail + '\'' +
                ", photoId='" + photoId + '\'' +
                ", adminKey=" + adminKey +
                ", wKey=" + wKey +
                ", endDate=" + endDate +
                ", room='" + room + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", wType='" + wType + '\'' +
                ", appointment=" + appointment +
                ", appointDate='" + appointDate + '\'' +
                '}';
    }
}
