package com.repairsys.dao;

import com.repairsys.bean.entity.Form;

import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/24 13:18
 */
public interface FormDao {

    /**
     * 根据维修单号来查询维修单的信息
     * @param formId 维修单号
     * @return 返回一条javabean对象
     */
    Form queryByFormId(String formId);

    /**
     * 根据学生学号来查询维修单的信息
     * @param stuId 学生学号
     * @return 返回一个Form的集合，因为一个学生可能提交多个维修单
     */
    List<Form> queryByStudentId(String stuId);


    /**
     * 查询指定状态的表单，比如查询未进行维修处理的表单 和 已经维修完成的表单
     * @param status 表单状态 如 0 表示待维修  1表示维修后7天内  2表示已经维修完成
     * @return @return 返回一条javabean 集合
     */
    List<Form> queryFormListByStatus(byte status);


    /**
     * 用户提交维修申请，需要将表单读写入数据库
     * @param args 表单的信息
     * @return  如果查询出现异常，插入数据失败，返回false，如果插入数据成功，返回true
     */
    Boolean apply(Object... args);

    /**
     * 用户申请表单提交
     * @param stuId 学生账号
     * @param code  表单状态
     * @param formMsg 表单内容详情
     * @param formDate 表单日期
     * @param formMail  用户的邮箱账号
     * @param photoId  用户发送的照片在服务器的地址存储路径
     * @return
     */
    Boolean apply(String stuId, int code, String formMsg, Date formDate,String formMail,String photoId);

    /**
     * 用户申请表单提交
     * @param stuId 学生账号
     * @param formMsg 学生表单报修详细情况
     * @param formDate 学生表单提交日期
     * @return 返回用户提交是否成功
     */
    Boolean apply(String stuId,String formMsg, Date formDate);

    /**
     * 维修成功后表单在数据库超过7天，管理员可能会手动删除记录，或者迁移记录，用来给管理员迁移记录到新的表的功能
     * @param form 表单的bean对象
     * @return 如果插入失败，返回false，否则返回true
     */
    Boolean apply(Form form);

    /**
     * 管理员可能要删除维修完成后，时间过久了的表单记录
     * @param formId 用户申请的表单 id
     * @return 如果删除失败，或者出现异常，返回false，否则返回true
     */
    Boolean delete(int formId);





}