package com.repairsys.bean.entity;

/**
 * @Author lyr
 * @create 2019/10/21 23:46
 */
public class ExcelTable {

    public static final String sql = "select w.wName,f.* from workers w right JOIN form f on w.wKey = f.wKey where f.wKey >0";
    private String wName;
    //工人名字
    private String formId;
    //form
    private String queryCode;
    private String formMsg;

    private String room;
    private String formDate;
    private String stuName;
    private String stuId;
    private String stuPhone;
    private String stuMail;
    private String appointDate;
    private String appointment;

    @Override
    public String toString() {
        return "ExcelTable{" +
                "wName='" + wName + '\'' +
                ", formId='" + formId + '\'' +
                ", queryCode='" + queryCode + '\'' +
                ", formMsg='" + formMsg + '\'' +

                ", room='" + room + '\'' +
                ", formDate='" + formDate + '\'' +
                ", stuName='" + stuName + '\'' +
                ", stuId='" + stuId + '\'' +
                ", stuPhone='" + stuPhone + '\'' +
                ", stuMail='" + stuMail + '\'' +
                ", appointDate='" + appointDate.substring(0,10) + '\'' +
                ", appointment='" + appointment + '\'' +
                '}'+"\r\n";
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getQueryCode() {
        return queryCode;
    }

    public void setQueryCode(String queryCode) {
        this.queryCode = queryCode;
    }

    public String getFormMsg() {
        return formMsg;
    }

    public void setFormMsg(String formMsg) {
        this.formMsg = formMsg;
    }



    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getFormDate() {
        return formDate;
    }

    public void setFormDate(String formDate) {
        this.formDate = formDate;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getStuPhone() {
        return stuPhone;
    }

    public void setStuPhone(String stuPhone) {
        this.stuPhone = stuPhone;
    }

    public String getStuMail() {
        return stuMail;
    }

    public void setStuMail(String stuMail) {
        this.stuMail = stuMail;
    }

    public String getAppointDate() {
        return appointDate;
    }

    public void setAppointDate(String appointDate) {
        this.appointDate = appointDate;
    }

    public String getAppointment() {
        return appointment;
    }

    public void setAppointment(String appointment) {
        this.appointment = appointment;
    }
}
