package com.repairsys.bean.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Prongs
 * @date 2019/10/9 10:08
 */
public class Board implements Serializable {
    private int key;
    private String boardMsg;
    private Date date;

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getBoardMsg() {
        return boardMsg;
    }

    public void setBoardMsg(String boardMsg) {
        this.boardMsg = boardMsg;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Board{" +
                "key=" + key +
                ", boardMsg='" + boardMsg + '\'' +
                ", date=" + date +
                '}';
    }
}
