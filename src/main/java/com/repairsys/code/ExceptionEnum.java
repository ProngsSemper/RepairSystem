package com.repairsys.code;

/**
 * @author lyr
 * @date 2019/9/21
 * <p>
 * 自定义异常
 * 使用枚举来描述异常
 */

public enum ExceptionEnum {
    /**
     * 自定义但是属于未知异常
     */
    UNKNOWN_ERROR(-1, "未知的异常"),


    /**
     * @date 2019/9/29
     * 用户修改信息的时候，填写的个人密码写错了，
     */
    USER_RESET_INFORMATION_ERROR(-2,"修改信息出现异常，可能是用户密码填写不正确"),
    /**
     * 文件上传异常
     */
    FILE_UPLOAD_ERROR(0, "图片上传失败"),

    /**
     * 访问学校的服务器出现了，学校的服务器奔溃了，无法处理请求
     * @date 2019/10/2
     *
     * */
    SERVER_CRASH(999,"学校教务系统崩溃");





    {
        this.code = -1;
        this.desc = "未知的异常";
    }
    ExceptionEnum(int i, String s) {
        this.code = i;
        this.desc = s;
    }

    private int code;
    private String desc;

    @Override
    public String toString() {
        return "E{ " +
                "状态码： " + code +
                ", 描述信息：'" + desc + '\'' +
                '}';
    }

    public String getDesc() {
        return desc;
    }

    public int getCode() {
        return code;
    }
}
