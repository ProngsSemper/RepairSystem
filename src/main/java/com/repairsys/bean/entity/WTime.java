package com.repairsys.bean.entity;

import java.sql.Date;
import java.util.Comparator;

/**
 * @Author lyr
 * @create 2019/10/8 12:55
 */
public class WTime implements Comparable<WTime>{



    private int tId;
    private int wKey;
    private Date curTime;
    private int t9,t10,t11,t14,t15,t16,t17,t18;
    private int score = 0;
    private String wType,wName,wMail,wTel;

    public String getwMail() {
        return wMail;
    }

    public void setwMail(String wMail) {
        this.wMail = wMail;
    }

    public String getwTel() {
        return wTel;
    }

    public void setwTel(String wTel) {
        this.wTel = wTel;
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

    public int getScore() {
        return score;
    }

    /**
     * 简单的记录分数，实现简单的推荐算法
     * @return 返回工人的任务总数
     */
    public int getSum()
    {

        return this.score;
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
        this.score+=t9;
    }

    public int getT10() {
        return t10;
    }

    public void setT10(int t10) {
        this.t10 = t10;
        this.score+=t10;
    }

    public int getT11() {
        return t11;
    }

    public void setT11(int t11) {
        this.t11 = t11;
        this.score+=t11;
    }

    public int getT14() {
        return t14;
    }

    public void setT14(int t14) {
        this.t14 = t14;
        this.score+=t14;
    }

    public int getT15() {
        return t15;
    }

    public void setT15(int t15) {
        this.t15 = t15;
        this.score+=t15;
    }

    public int getT16() {
        return t16;
    }

    public void setT16(int t16) {
        this.t16 = t16;
        this.score+=t16;
    }

    public int getT17() {
        return t17;
    }

    public void setT17(int t17) {
        this.t17 = t17;
        this.score+=t17;
    }

    public int getT18() {
        return t18;
    }

    public void setT18(int t18) {
        this.t18 = t18;
        this.score+=t18;

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
                ", t18=" + t18 + this.wType+
                "}\r\n";
    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     *
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param o the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */
    @Override
    public int compareTo(WTime o) {

        return o.getwKey()-this.getwKey();
    }

    public int getTimeAt(int point)
    {
        switch (point) {
            case 0:{
                return this.getT9();
            }
            case 1:{
                return this.getT10();
            }
            case 2:{
                return this.getT11();
            }
            case 3:{
                return this.getT14();
            }
            case 4:{
                return this.getT15();
            }
            case 5:{
                return this.getT16();
            }
            case 6:{
                return this.getT17();
            }
            case 7:{
                return this.getT18();
            }
            default:
            {
                return 0;
            }
        }

    }
    public int getHourAt(int hour)
    {
        switch (hour)
        {
            case 9:{
                return t9;
            }
            case 10:
            {
                return t10;
            }
            case 11:{
                return t11;
            }
            case 14:{
                return t14;
            }
            case 15:{
                return t15;
            }
            case 16:{
                return t16;
            }
            case 17:{
                return t17;
            }
            case 18:{
                return t18;
            }
            default:{
                return score;
            }
        }
    }






}
