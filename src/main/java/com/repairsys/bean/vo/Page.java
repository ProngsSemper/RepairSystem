package com.repairsys.bean.vo;

/**
 * @author Prongs
 * @date 2019/10/2 14:16
 */
public class Page<T> extends Result<T> {
    public Page() {
    }

    private int totalPage = -1;
    private int totalCount = -1;
    private int targetPage = -1;
    private int size = -1;
    // private int count = 0;

    public Page(int code, T data, String desc) {
        super(code, data, desc);
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;

    }

    public int getTargetPage() {
        return targetPage;
    }

    public void setTargetPage(int targetPage) {
        this.targetPage = targetPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }


}
