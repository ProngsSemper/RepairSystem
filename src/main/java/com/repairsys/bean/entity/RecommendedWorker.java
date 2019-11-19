package com.repairsys.bean.entity;

import java.util.Comparator;

/**
 * @Author lyr
 * @create 2019/11/16 14:03
 */
public class RecommendedWorker{


    @Override
    public String toString() {
        return "RecommendedWorker{" +
                "t9=" + t9 +
                ", t10=" + t10 +
                ", t11=" + t11 +
                ", t14=" + t14 +
                ", t15=" + t15 +
                ", t16=" + t16 +
                ", t17=" + t17 +
                ", t18=" + t18 +
                ", total=" + total +
                ", locationCount=" + locationCount +
                ", wMail='" + wMail + '\'' +
                ", wName='" + wName + '\'' +
                ", wType='" + wType + '\'' +
                ", wTel='" + wTel + '\'' +
                '}';
    }

    private int t9,t10,t11,t14,t15,t16,t17,t18;
    private int total;
    private int locationCount;
    private String wMail;
    private String wName;
    private String wType;
    private String wTel;
    private int wKey;

    public int getwKey() {
        return wKey;
    }

    public void setwKey(int wKey) {
        this.wKey = wKey;
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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(int locationCount) {
        this.locationCount = locationCount;
    }

    
    public String getwMail() {
        return wMail;
    }

    
    public void setwMail(String wMail) {
        this.wMail = wMail;
    }

    
    public String getwName() {
        return wName;
    }

    
    public void setwName(String wName) {
        this.wName = wName;
    }

    
    public String getwType() {
        return wType;
    }

    
    public void setwType(String wType) {
        this.wType = wType;
    }

    
    public String getwTel() {
        return wTel;
    }

    
    public void setwTel(String wTel) {
        this.wTel = wTel;
    }

    public int getTime(int hour)
    {
        int ans = 0;
        if(hour<=14)
        {
            switch (hour)
            {
                case 9:{
                    ans = t9;
                    break;
                }
                case 10:
                {
                    ans = t10;
                    break;
                }
                case 11:{
                    ans = t11;
                    break;
                }
                case 14:{
                    ans = t14;
                    break;
                }
                default:{

                }
            }
        }else{

            switch (hour)
            {
                case 15:{
                    ans = t15;break;
                }
                case 16:{
                    ans = t16;break;
                }
                case 17:{
                    ans = t17;break;
                }
                case 18:{
                    ans = t18;break;
                }
                default:{}
            }


        }

        return ans;
    }


}
