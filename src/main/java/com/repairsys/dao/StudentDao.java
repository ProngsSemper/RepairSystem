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
     * @return
     */
    Student queryById(String stuId);

    /**
     * 学生登录
     *
     * @param stuId 学生学号
     * @param password 学生账号
     * @return
     */
    Student login(String stuId,String password );

    /**
     * 学生是否存在
     * @param stuId 
     * @param password
     * @return
     */
    boolean exists(String stuId,String password);

    /**
     * 学生注册
     * @param person
     * @return
     */
    boolean register(Student person);





}
