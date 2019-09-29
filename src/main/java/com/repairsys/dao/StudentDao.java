package com.repairsys.dao;

import com.repairsys.bean.entity.Student;

/**
 * @Author lyr
 * @create 2019/9/24 13:28
 */
public interface StudentDao {

    /**
     * 根据学生学号进行查询
     * @param stuId 学生学号
     * @return 返回一个bean对象 (Student 类)
     */
    Student queryById(String stuId);

    /**
     * 学生登录
     *
     * @param stuId 学生学号
     * @param password 学生账号
     * @return 返回一个bean对象 (Student 类)
     */
    Student login(String stuId,String password );

    /**
     * 学生是否存在
     * @param stuId 学生的 id账号
     *
     * @return 学生是否存在
     */
    boolean exists(String stuId);

    /**
     * 学生注册
     * @param person
     * @return 学生注册账号是否成功
     */
    boolean register(Student person);



    /**
     * 工人注册时，写入数据库的一条记录
     * @param stuId 工人的登录账号
     * @param stuName 工人的名字
     * @param stuTel 工人的电话号码
     * @param stuPassword 工人的账号密码
     * @param stuMail 工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false
     */
    boolean register(String stuId,String stuName,String stuTel,String stuPassword,String stuMail);


    /**
     * 修改学生密码
     * @param stuId 学习账号
     * @param newPassword 学生密码
     * @return 修改数据库的密码是否成功，成功返回true，出现异常返回 false
     */
    boolean resetPassword(String stuId,String newPassword);


    /**
     * 学生在不记得密码的情况下修改密码
     * @param stuId  stuId 学习账号
     * @param password  学生旧密码
     * @param newPassword  学生旧密码
     * @return 修改数据库的密码是否成功，成功返回true，出现异常返回 false
     */
    boolean resetPassword(String stuId, String password,String newPassword);







}
