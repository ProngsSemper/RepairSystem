package com.repairsys.code;

/**
 * @author 林洋锐，Prongs
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
     * 文件上传异常
     */
    FILE_UPLOAD_ERROR(0, "图片上传失败");

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
