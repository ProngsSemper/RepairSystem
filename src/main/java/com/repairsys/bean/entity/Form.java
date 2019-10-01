package com.repairsys.bean.entity;

import java.io.Serializable;
import java.sql.Date;


/**
 * @Author lyr
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

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    private Date endDate;

    public Form(){}

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
                '}';
    }
}
