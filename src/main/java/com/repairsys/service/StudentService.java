package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/9 8:11
 */
public interface StudentService {
    /**
     * 学生登录
     *
     * @param stuId       学生学号
     * @param stuPassword 学生在教务系统的密码
     * @param session
     * @return 返回相应状态码
     */
    Result<Boolean> login(String stuId, String stuPassword, HttpSession session);

    /**
     * 查询所有历史公告
     *
     * @param page  当前页
     * @param limit 每页最多显示多少条数据
     * @return 返回数据库中board表的数据
     */
    Result getHistoryBoard(int page, int limit);

    /**
     * 查询最新的学校公告 即queryCode=1的公告
     *
     * @return 返回一条最新的学校公告
     */
    Result getBoard();
}
