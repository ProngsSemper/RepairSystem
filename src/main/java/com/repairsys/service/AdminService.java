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
     * @return
     */
    Result<Boolean> login(String adminId, String password, HttpSession session);
}
