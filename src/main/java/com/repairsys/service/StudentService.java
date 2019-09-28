package com.repairsys.service;

import com.repairsys.bean.entity.Form;
import com.repairsys.bean.vo.Result;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/9/28
 */
public interface StudentService {
    /**
     * 学生登录
     * @param stuId 学生账号（学号）
     * @param password 学生密码
     * @param session 用户传入的session
     * @return 返回一个result对象 ，controller层将其转为json
     */
    Result<Boolean> login(String stuId, String password, HttpSession session);


    /**
     * 学生注册
     * @param stuId 学生账号（学号）
     * @param password 学生密码
     * @param session 用户传入的session
     * @return 返回一个result对象 ，controller层将其转为json
     */
    Result<Boolean> register(String stuId, String password, HttpSession session);


    /**
     * 学生在记得密码的情况下修改密码
     * @param stuId 学生账号（学号）
     * @param password 学生密码
     * @return 返回一个result对象 ，controller层将其转为json
     */
    Result<Boolean> setPassword(String stuId,String password);

    /**
     * 学生在忘记密码的情况下修改密码
     * @param stuId 学生账号
     * @return 返回一个result对象 ，controller层将其转为json
     */
    Result<Boolean> setPassword(String stuId);


    /**
     * 学生要修改的信息
     * @param stuId 学生账号
     * @param password 学生密码
     * @param columnName 数据库的列名
     * @param value  数据库列对应的值
     * @return 返回一个记录学生修改是否成功的Result对象，后面处理为 json格式
     */
    Result<Boolean> modifyInformation(String stuId,String password,String columnName,String value);


    /**
     * 学生要修改的信息
     * @param stuId 学生账号
     * @param password 学生密码
     * @param columnNames 修改数据库的多个列名
     * @param values  修改数据库多个列对应的值
     * @return 返回一个记录学生修改是否成功的Result对象，后面处理为 json格式
     */
    Result<Boolean> modifyInformation(String stuId,String password,String[] columnNames,String[] values);


    /**
     * 学生注销账户
     * @param stuId 学生账号
     * @param password 学习密码
     * @return 返回是否注销成功
     */
    Result<Boolean> deleteStudent(String stuId,String password);

    /**
     * 学生查询正在申请的维修表单
     * @param stuId 学生账号
     * @return 返回学习提交的所有表单的信息
     */
    Result<List<Form>> getFormList(String stuId);

    /**
     * 学生查询维修表单历史记录
     * @param stuId 学生账号
     * @return 返回学习提交的所有表单的信息
     */
    Result<List<Form>> getOldFormList(String stuId);

}
