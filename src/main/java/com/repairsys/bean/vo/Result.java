package com.repairsys.bean.vo;

import com.repairsys.code.ResultEnum;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 数据库得到的结果集合
 * 比如说状态信息，数据库查询的实体类，
 * 最后要给用户转化 为 json格式的数据
 */
public class Result<T> {

    /**
     * 返回状态码
     */
    protected int code;
    /**
     * 返回数据
     */
    protected T data;

    /**
     * 返回的描述信息
     */
    protected String desc;

    public Result() {
        super();
    }

    public Result(int code, T data, String desc) {
        this.code = code;
        this.data = data;
        this.desc = desc;
    }

    public Result<T> setResult(ResultEnum info) {
        this.code = info.getCode();
        this.desc = info.getDesc();
        return this;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code=" + code +
                ", data=" + data +
                ", desc='" + desc + '\'' +
                '}';
    }
}
