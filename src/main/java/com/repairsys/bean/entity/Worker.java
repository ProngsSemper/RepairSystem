package com.repairsys.bean.entity;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

/**
 * @Author lyr
 * @create 2019/9/24 12:11
 */
public class Worker implements Serializable, Comparable<Worker> {

    public static class CompareHandler implements Comparator<Worker> {

        @Override
        public int compare(Worker o1, Worker o2) {
            return o1.score - o2.score;
        }
    }

    private String wId;
    private String wName;
    private String wTel;
    private String wPassword;
    private String wMail;
    private String wType;
    private int wKey;
    private int score = -1;
    private int good;
    private int mid;
    private int bad;
    private String wToken;

    public Worker() {
    }

    public Worker(WTime w) {
        this.wKey = w.getwKey();
        this.wName = w.getwName();
        this.wType = w.getwType();
        this.score = w.getScore();
        this.wMail = w.getwMail();
        this.wTel = w.getwTel();

    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getwMail() {
        return wMail;
    }

    public void setwMail(String wMail) {
        this.wMail = wMail;
    }

    public int getwKey() {
        return wKey;
    }

    public void setwKey(int wKey) {
        this.wKey = wKey;
    }

    public String getwId() {
        return wId;
    }

    public void setwId(String wId) {
        this.wId = wId;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    public String getwTel() {
        return wTel;
    }

    public void setwTel(String wTel) {
        this.wTel = wTel;
    }

    public String getwPassword() {
        return wPassword;
    }

    public void setwPassword(String wPassword) {
        this.wPassword = wPassword;
    }

    public String getwType() {
        return wType;
    }

    public void setwType(String wType) {
        this.wType = wType;
    }

    public int getGood() {
        return good;
    }

    public void setGood(int good) {
        this.good = good;
    }

    public int getMid() {
        return mid;
    }

    public void setMid(int mid) {
        this.mid = mid;
    }

    public int getBad() {
        return bad;
    }

    public void setBad(int bad) {
        this.bad = bad;
    }

    public String getwToken() {
        return wToken;
    }

    public void setwToken(String wToken) {
        this.wToken = wToken;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "wId='" + wId + '\'' +
                ", wName='" + wName + '\'' +
                ", wTel='" + wTel + '\'' +
                ", wPassword='" + wPassword + '\'' +
                ", wMail='" + wMail + '\'' +
                ", wType='" + wType + '\'' +
                ", wKey=" + wKey +
                ", score=" + score +
                ", good=" + good +
                ", mid=" + mid +
                ", bad=" + bad +
                ", wToken='" + wToken + '\'' +
                '}';
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
    public int compareTo(Worker o) {
        return o.wKey - this.wKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Worker worker = (Worker) o;
        return worker.getwKey() == ((Worker) o).getwKey();
    }

    @Override
    public int hashCode() {
        return Objects.hash(wKey);
    }

}
