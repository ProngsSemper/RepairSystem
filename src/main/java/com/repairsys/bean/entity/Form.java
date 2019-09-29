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
    private String formMail;
    private String photoId;

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

    public String getFormMail() {
        return formMail;
    }

    public void setFormMail(String formMail) {
        this.formMail = formMail;
    }

    public String getPhotoId() {
        return photoId;
    }

    public void setPhotoId(String photoId) {
        this.photoId = photoId;
    }
}
