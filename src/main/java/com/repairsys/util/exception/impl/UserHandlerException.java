package com.repairsys.util.exception.impl;

import com.repairsys.code.ExceptionEnum;
import com.repairsys.util.exception.BaseException;

/**
 * @Author lyr
 * @create 2019/9/29 12:26
 * <p>
 * 用户操作个人信息出现异常，比如修改密码，当时原来填写的密码不对，抛出此异常，让用户重新输入密码验证
 */
public final class UserHandlerException extends BaseException {

    public UserHandlerException() {
        super(ExceptionEnum.USER_RESET_INFORMATION_ERROR);
    }
}
