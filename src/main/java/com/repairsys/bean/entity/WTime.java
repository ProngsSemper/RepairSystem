package com.repairsys.bean.entity;

import java.sql.Date;

/**
 * @Author lyr
 * @create 2019/10/8 12:55
 */
public class WTime {
    private int tId;
    private int wKey;
    private Date curTime;
    private int t9,t10,t11,t14,t15,t16,t17,t18;

    public int getSum()
    {
        return t9+t10+t11+t14+t15+t16+t17+t18;
    }

    public int gettId() {
        return tId;
    }

    public void settId(int tId) {
        this.tId = tId;
    }

    public int getwKey() {
        return wKey;
    }

    public void setwKey(int wKey) {
        this.wKey = wKey;
    }

    public Date getCurTime() {
        return curTime;
    }

    public void setCurTime(Date curTime) {
        this.curTime = curTime;
    }

    public int getT9() {
        return t9;
    }

    public void setT9(int t9) {
        this.t9 = t9;
    }

    public int getT10() {
        return t10;
    }

    public void setT10(int t10) {
        this.t10 = t10;
    }

    public int getT11() {
        return t11;
    }

    public void setT11(int t11) {
        this.t11 = t11;
    }

    public int getT14() {
        return t14;
    }

    public void setT14(int t14) {
        this.t14 = t14;
    }

    public int getT15() {
        return t15;
    }

    public void setT15(int t15) {
        this.t15 = t15;
    }

    public int getT16() {
        return t16;
    }

    public void setT16(int t16) {
        this.t16 = t16;
    }

    public int getT17() {
        return t17;
    }

    public void setT17(int t17) {
        this.t17 = t17;
    }

    public int getT18() {
        return t18;
    }

    public void setT18(int t18) {
        this.t18 = t18;
    }


    @Override
    public String toString() {
        return "WTime{" +
                "tId=" + tId +
                ", wKey=" + wKey +
                ", curTime=" + curTime +
                ", t9=" + t9 +
                ", t10=" + t10 +
                ", t11=" + t11 +
                ", t14=" + t14 +
                ", t15=" + t15 +
                ", t16=" + t16 +
                ", t17=" + t17 +
                ", t18=" + t18 +
                "}\r\n";
    }
}
