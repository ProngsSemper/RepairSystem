package com.repairsys.util.exception;

import com.repairsys.code.ExceptionEnum;

/**
 * @author lyr
 * @date 2019/9/22
 * <p>
 * 自定义异常，配合 枚举类 ExceptionEnum的信息使用
 */
public class BaseException extends RuntimeException {
    /**
     * 异常状态码
     */
    private Integer code;
    /**
     * 异常信息
     */
    private String msg;

    /**
     * Constructs a new runtime exception with {@code null} as its
     * detail message.  The cause is not initialized, and may subsequently be
     * initialized by a call to {@link #initCause}.
     */
    public BaseException() {
        this(ExceptionEnum.UNKNOWN_ERROR);
    }

    public BaseException(ExceptionEnum e) {
        this.code = e.getCode();
        this.msg = e.getDesc();
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "BaseException{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}
