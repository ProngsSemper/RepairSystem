package com.repairsys.service;

import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;

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

    /**
     * 学生提交报修单
     *
     * @param stuId     学生学号
     * @param queryCode 查询码
     * @param formMsg   表单详情
     * @param formDate  申请日期
     * @param stuMail   学生的 email
     * @param photoId   学生上传的照片
     * @param room      学生宿舍
     * @param stuName   学生姓名
     * @param stuPhone  学生手机
     * @param wType 报修种类
     * @return
     */
    Result applyForm(String stuId, int queryCode, String formMsg, Timestamp formDate, String stuMail, String photoId, String room, String stuName, String stuPhone,String wType);

//    apply(String stuId, int code, String formMsg, Date formDate, String stuMail, String photoId)
}
