package com.repairsys.code;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 定义结果状态，反馈给前端的信息
 */

public enum ResultEnum {

    /**
     * 分页查询成功
     */
    SHOW_TABLE(0, "分页查询状态码：查询超过"),

    /**
     * 登录失败
     */
    LOGIN_FAIL(401, "用户名或密码错误"),

    /**
     * 登录成功
     */
    LOGIN_SUCCESS(200, "登录成功/服务器成功返回数据"),

    /**
     *
     * 查询成功
     * */
    QUERY_SUCESS(201,"查询成功"),

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
     *
     * 表单重复提交，一小时内无法再提交
     *
     * */
    SUBMITTED_REPEATLY(405,"后台检测表单已经提交过了，一小时内不允许重复提交"),

    SUBMITTED_FAILED(400, "报修单提交失败"),
    /**
     * 修改报修单状态成功
     */
    UPDATE_QUERYCODE_SUCCESSFULLY(201, "修改报修单状态成功"),
    /**
     * 修改报修单状态失败
     */
    UPDATE_QUERYCODE_FAILED(400, "修改报修单状态失败"),
    /**
     * 修改优先级成功
     */
    BOOST_SUCCESSFULLY(201, "修改优先级成功"),
    /**
     * 修改优先级失败
     */
    BOOST_FAILED(400, "修改优先级失败"),
    /**
     * 确认成功
     */
    CONFIRM_SUCCESSFULLY(201, "确认成功"),
    /**
     * 确认失败
     */
    CONFIRM_FAILED(400, "确认失败"),
    /**
     * 确认成功
     */
    DELETE_SUCCESSFULLY(201, "删除成功"),
    /**
     * 确认失败
     */
    DELETE_FAILED(400, "删除失败"),
    /**
     * 评价成功
     */
    EVALUATE_SUCCESSFULLY(201, "评价成功"),
    /**
     * 评价失败
     */
    EVALUATE_FAILED(400, "评价失败"),
    /**
     * 评价成功
     */
    APPOINT_SUCCESSFULLY(201, "修改预约时间成功"),
    /**
     * 评价失败
     */
    APPOINT_FAILED(400, "修改预约时间失败"),
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

    /**
     * 公告发布成功
     *
     * @author Prongs
     * @date 2019/10/08
     */
    RELEASE_SUCCESSFULLY(200, "公告发布成功");

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














