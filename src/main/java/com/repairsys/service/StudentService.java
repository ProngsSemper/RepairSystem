package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;

/**
 * @author Prongs
 */
public interface StudentService {
    /**
     * 学生登录
     * @param stuId 学生账号（学号）
     * @param password 学生密码
     * @param session 用户传入的session
     * @return
     */
    Result<Boolean> login(String stuId, String password, HttpSession session);
}
