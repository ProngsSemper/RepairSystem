package com.repairsys.vo;

/**
 * @author 林洋锐
 * @date 2019/9/21
 *
 * 数据库得到的结果集合
 * 比如说状态信息，数据库查询的实体类，
 * 最后要给用户转化 为 json格式的数据
 *
 */
public class Result<T> {

    /** 返回状态码 */
    private int code;
    /** 返回数据 */
    private T data;

    /** 返回的描述信息 */
    private String desc;


    public Result() {
        super();
    }

    public Result(int code,T data,String desc)
    {
        this.code = code;
        this.data = data;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public T getData() {
        return data;
    }

    public String getDesc() {
        return desc;
    }
}
