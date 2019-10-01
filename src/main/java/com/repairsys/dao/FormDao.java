package com.repairsys.dao;

import com.repairsys.bean.entity.Form;

import java.sql.Date;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/1 21:50
 */
public interface FormDao {
    /**
     * 根据维修单号来查询维修单的信息
     *
     * @param formId 维修单号
     * @return 返回一条javabean对象
     */
    List<Form> queryByFormId(String formId);

    /**
     * 根据学生学号来查询维修单的信息
     *
     * @param stuId 学生学号
     * @return 返回一个Form的集合，因为一个学生可能提交多个维修单
     */
    List<Form> queryByStudentId(String stuId);

    /**
     * 查询指定状态的表单，比如查询未进行维修处理的表单 和 已经维修完成的表单
     *
     * @param status 表单状态 如 0 表示待维修  1表示维修后7天内  2表示已经维修完成
     * @return @return 返回一条javabean 集合
     */
    List<Form> queryFormListByStatus(byte status);

    /**
     * 用户提交维修申请，需要将表单读写入数据库
     *
     * @param args 表单的信息
     * @return 如果查询出现异常，插入数据失败，返回false，如果插入数据成功，返回true
     * @deprecated
     */
    @Deprecated
    Boolean apply(Object... args);

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param code     表单状态
     * @param formMsg  表单内容详情
     * @param formDate 表单日期
     * @param formMail 用户的邮箱账号
     * @param photoId  用户发送的照片在服务器的地址存储路径
     * @return
     */
    Boolean apply(String stuId, int code, String formMsg, Date formDate, String formMail, String photoId);

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param formMsg  学生表单报修详细情况
     * @param formDate 学生表单提交日期
     * @return 返回用户提交是否成功
     */
    Boolean apply(String stuId, String formMsg, Date formDate);

    /**
     * 维修成功后表单在数据库超过7天，管理员可能会手动删除记录，或者迁移记录，用来给管理员迁移记录到新的表的功能
     *
     * @param form 表单的bean对象
     * @return 如果插入失败，返回false，否则返回true
     */
    Boolean apply(Form form);

    /**
     * 管理员可能要删除维修完成后，时间过久了的表单记录
     *
     * @param tableName 删除指定表的数据
     * @param date      删除在该日期前的数据
     * @return 如果删除失败，或者出现异常，返回false，否则返回true
     */

    Boolean deleteBefore(String tableName, java.sql.Date date);

    /**
     * 将超过 7天的数据迁移到 oldform 表中
     *
     * @return 返回操作是否成功
     */
    Boolean moveTo();

    /**
     * 将超过 7天的数据迁移到 oldform 表中
     * 更加语义化的函数，建议直接用这个
     *
     * @return 操作无异常，返回true
     */
    Boolean updateTable();

    /**
     * 对应的管理员处理的信息
     *
     * @param adminKey 设置管理员记录，哪个管理员来处理对应的申请
     * @param formId   对应的表单的主键id号码
     * @return 若操作无异常，返回true
     */
    Boolean setAdminKey(String adminKey, String formId);

    /**
     * 设置对应的工人，由管理员手动分配
     *
     * @param workerKey 设置对于的工人的 信息主键
     * @param formId    设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    Boolean setwKey(String workerKey, String formId);

    /**
     * 设置照片的信息，学生上传照片，照片的 url地址保存在一个 photo表里面，记录photo 表信息的主键
     *
     * @param formId 设置对应的表单的主键 id号码
     * @param id     设置学生上传照片在服务器的地址
     * @return 若操作无异常，返回true
     */
    Boolean setPhotoId(String id, String formId);

    /**
     * 工作完成，工人抹除任务
     *
     * @param formId 设置对应的表单的主键 id号码
     * @param date   维修完成的日期
     * @return 若操作无异常，返回true
     */
    Boolean setEndDate(Date date, String formId);

    /**
     * 设置任务完成状态，学生申请： 0，管理员审核 ：1 ，工人维修完成 2
     *
     * @param code   维修单的状态码，完成进度
     * @param formId 设置对应的表单的主键 id号码
     * @return 若操作无异常，返回true
     */
    Boolean setQueryCode(int code, String formId);

    /**
     * 管理员查看了学生的申请，并为其分配维修工人和安排时间
     *
     * @param formId    设置对应的表单的主键 id号码
     * @param endDate   维修结束时间
     * @param queryCode 维修查询状态码
     * @param adminKey  管理员的key主键
     * @param wKey      工人的key主键
     * @return 如果操作无异常，返回true
     * @date 2019/10/1
     */
    Boolean updateForm(String formId, Date endDate, int queryCode, int adminKey, int wKey);

}
