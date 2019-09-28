package com.repairsys.dao.impl;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.FormDao;
import com.repairsys.util.db.JdbcUtil;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/26 23:23
 */
public class FormDaoImpl extends BaseDao<Form> implements FormDao {

    private static final String QUERY_BY_FORMID = "select * from form where `formId` = ?";
    private static final String QUERY_BY_STUDENTID = "select * from form where `stuId` = ?";
    private static final String APPLY_FORM = "INSERT INTO FORM (stuId,queryCode,formId,formMsg,formDate,formMail,photoId,adminKey)values(?,?,?,?,?,?,?,?)";
    private static final String APPLY_FORM_DEFAULT = "INSERT INTO FORM (stuId,queryCode,formMsg,formDate,formMail,photoId)values(?,?,?,?,?,?)";
    private static final FormDaoImpl FORM_DAO= new FormDaoImpl();

    private FormDaoImpl() {
        super(Form.class);
    }
    public static FormDaoImpl getInstance(){return FORM_DAO;}

    /**
     * 根据维修单号来查询维修单的信息
     *
     * @param formId 维修单号
     * @return 返回表单bean对象
     */
    @Override
    public Form queryByFormId(String formId) {
        Connection conn = JdbcUtil.getConnection();

        return super.selectOne(conn,QUERY_BY_FORMID,formId);
    }

    /**
     * 根据学号来查询维修单的信息
     *
     * @param stuId 学生学号
     * @return 返回表单bean对象
     */
    @Override
    public List<Form> queryByStudentId(String stuId) {

        Connection conn = JdbcUtil.getConnection();
        return super.selectList(conn,QUERY_BY_STUDENTID,stuId);
    }

    /**
     * 查询指定状态的表单，比如查询未进行维修处理的表单 和 已经维修完成的表单
     *
     * @param status 表单状态 如 0 表示待维修  1表示维修后7天内  2表示已经维修完成
     * @return 返回表单bean 集合(List)
     */
    @Override
    public List<Form> queryFormListByStatus(byte status) {
        //TODO: 还没定下来使用场景
        return super.selectList(JdbcUtil.getConnection(),"",status);
    }

    /**
     * 用户提交维修申请，需要将表单读写入数据库
     *
     * @param args 表单的信息
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     * @deprecated 请不要直接使用，Object不定长参数不安全，而且该函数已经内定好sql语句了
     */
    @Deprecated
    @Override
    public Boolean apply(Object... args) {


        Connection conn = JdbcUtil.getConnection();
        return super.addOne(conn, APPLY_FORM, args);


    }

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param code     表单状态: 例如 0表示 false，即没有维修， 1表示维修完成后7天内
     * @param formMsg  表单内容详情
     * @param formDate 表单日期
     * @param formMail 用户的邮箱账号
     * @param photoId  用户发送的照片在服务器的地址存储路径
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     */
    @Override
    public Boolean apply(String stuId, int code, String formMsg, Date formDate, String formMail, String photoId) {
        //INSERT INTO FORM (stuId,queryCode,formMsg,formDate,formMail,photoId)";
        return super.addOne(JdbcUtil.getConnection(), APPLY_FORM_DEFAULT, stuId, code, formMsg, formDate, formMail, photoId);
    }

    /**
     * 用户申请表单提交
     *
     * @param stuId    学生账号
     * @param formMsg  学生表单报修详细情况
     * @param formDate 学生表单提交日期
     * @return 返回布尔值，如果提交成功返回true，如果运行出现异常，返回false
     */
    @Override
    public Boolean apply(String stuId, String formMsg, Date formDate) {

        return this.apply(stuId,0,formMsg,formDate,"","");
    }


    //TODO:暂时还未完成，需要具体讨论

    /**
     * 维修成功后表单在数据库超过7天，管理员可能会手动删除记录，或者迁移记录，用来给管理员迁移记录到新的表的功能
     *
     * @param form 表单的bean对象
     * @return 如果插入失败，返回false，否则返回true
     */
    @Override
    public Boolean apply(Form form) {
        return false;
    }


    //TODO:暂时还未完成，需要具体讨论

    /**
     * 管理员可能要删除维修完成后，时间过久了的表单记录，根据表单的 id号进行删除
     *
     * @param formId 表单的id 号
     * @return 如果删除失败，或者出现异常，返回false，否则返回true
     */
    @Override
    public Boolean delete(int formId) {
        return false;
    }
}
