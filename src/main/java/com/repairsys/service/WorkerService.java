package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;

/**
 * @author Prongs
 * @date 2019/9/29 15:57
 */
public interface WorkerService {
    /**
     * 工人登录
     *
     * @param wId      用户账号
     * @param password 用户密码
     * @param session  用户传入的 session
     * @return
     */
    Result<Boolean> login(String wId, String password, HttpSession session);
}
