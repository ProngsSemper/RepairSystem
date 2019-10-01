package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;

/**
 * @Author lyr,Prongs
 * @create 2019/9/24 18:07
 */
public interface AdminService {

    /**
     * 管理员登录
     * @param adminId 用户账号
     * @param password 用户密码
     * @param session 用户传入的 session
     * @return 返回对应状态码
     */
    Result<Boolean> login(String adminId, String password, HttpSession session);

    /**
     * 管理员根据报修单号查询报修单
     * @param formId 报修单号
     * @return 返回对应状态码
     */
    Result getByFormId(String formId);

    /**
     * 管理员根据学生学号查询报修单
     * @param stuId 学生学号
     * @return 返回对应状态码
     */
    Result getByStudentId(String stuId);
}
