package com.repairsys.dao;

/**
 * @author Prongs
 * @date 2019/10/30 10:57
 */
public interface FeedbackDao {
    /**
     * 学生创建反馈
     *
     * @param stuId    记录学生id
     * @param stuName  记录学生名字
     * @param stuPhone 记录学生电话
     * @param msg      记录学生反馈信息
     */
    void createFeedback(String stuId, String stuName, String stuPhone, String msg);
}
