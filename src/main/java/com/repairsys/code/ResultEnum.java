package com.repairsys.code;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 定义结果状态，反馈给前端的信息
 */

public enum ResultEnum {

    /**
     * 登录失败
     */
    LOGIN_FAIL(401, "用户名或密码错误"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(200, "登录成功"),

    /**
     * 用户名或密码不能为空
     */
    USERNAME_PASSWORD_EMPTY(402, "用户名或密码不能为空"),

    /**
     * 找不到此页面
     */
    PAGE_NOT_FOUND(404, "找不到此页面"),

    /**
     *
     *
     * @author lyr
     * @date 2019/9/29
     * <p>用户注册或者操作自己信息时，修改失败</p>
     * <p>广义化的操作失败</p>
     *
     * 用处于：学生重复注册提交账号信息，数据库报异常
     *
     *
     * */
    USER_DO_FAIL(407,"操作失败"),

    /**
     * 无访问权限
     */
    NO_PERMISSION(401, "您无权访问该页面"),

    /**
     * 报修单提交成功
     */
    SUBMITTED_SUCCESSFULLY(201, "报修单提交成功"),
    /**
     * @author lyr
     * @date 2019/9/29
     * 插入信息成功
     * <p>广义化的写入数据成功</p>
     *
     * 用处：学生注册提交成功
     *
     */
    COMMITED_SUCCESSFULLY(202, "写入信息成功"),

    ;

    /**
     * 状态码
     */
    private Integer code;
    /**
     * 状态描述
     */
    private String desc;

    ResultEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /*
     *
     * 下面是对 属性的 getter 方法
     *
     * */

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }



}














