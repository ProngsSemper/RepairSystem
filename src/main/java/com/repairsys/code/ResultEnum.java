package com.repairsys.code;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 定义结果状态，反馈给前端的信息
 */

public enum ResultEnum {

    /**
     * lay-ui 的状态码：分页查询成功
     */
    SHOW_TABLE(0, "分页查询状态码：查询超过"),

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

    CODE_FALSE(403, "验证码错误"),

    /**
     * 找不到此页面
     */
    PAGE_NOT_FOUND(404, "找不到此页面"),

    /**
     * @author lyr
     * @date 2019/9/29
     * 用法：用户修改密码或者修改信息，但是原来的密码填错了，抛出异常，需要回馈给页面
     */
    USER_DO_RESET_FAIL(408, "用户修改信息失败"),

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
     * <p>
     * 用处：学生注册提交成功
     */
    COMMITED_SUCCESSFULLY(202, "写入信息成功"),

    /**
     * @author Prongs
     * @date 2019/9/30
     * 查询报修表成功
     */
    QUERY_SUCCESSFULLY(200, "查询成功"),

    /**
     * @author Prongs
     * @date 2019/9/30
     * 没有查询到相关表单
     */
    QUERY_FAILED(401, "没有查询到相关表单"),

    /**
     * @author Prongs
     * @date 2019/9/30
     * 没有输入查询条件
     */
    QUERY_EMPTY(400, "请输入查询条件"),

    SEND_MAIL_SUCCESSFULLY(200, "邮件发送成功"),

    /**
     * 发件方身份没有得到认证
     *
     * @author Prongs
     * @date 2019/10/02
     */
    SEND_MAIL_FAILED(553, "发送失败"),

    ;

    /**
     * 状态码   code
     */
    private Integer code;
    /**
     * 状态描述 description
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














