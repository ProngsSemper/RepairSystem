package com.repairsys.dao;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/24 13:18
 */
public interface FormDao {

    /**
     * 根据维修单号来查询维修单的信息
     * @param formId 维修单号
     * @return
     */
    FormDao queryByFormId(String formId);

    /**
     * 根据学号来查询维修单的信息
     * @param stuId 学生学号
     * @return
     */
    FormDao queryByStudentId(String stuId);


    /**
     * 查询指定状态的表单，比如查询未进行维修处理的表单 和 已经维修完成的表单
     * @param status 表单状态 如 0 表示待维修  1表示维修后7天内  2表示已经维修完成
     * @return
     */
    List<FormDao> queryFormListByStatus(byte status);




}
