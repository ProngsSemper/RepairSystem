package com.repairsys.dao.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/30 18:50
 */
public final class FormListDaoImpl extends FormDaoImpl {
    /**
     * 根据学生的id号码 和 需要查询的表单状态进行查询
     */
    private static final String BASE_QUERY_BY_ID = "select * from form where queryCode = ? and stuId = ?";
    private static final String BASE_QUERY = "select * from form where queryCode = ? ";
    private static final String BASE_PAGE_LIST = "select * from form  where queryCode =0 limit ?,?";

    private static final FormListDaoImpl DAO = new FormListDaoImpl();

    private FormListDaoImpl() {
        super();
    }

    public static FormListDaoImpl getInstance() {
        return DAO;
    }

    public final List<Form> getListById(String stuId, int queryCode, String... formId) {
        if (formId.length == 0) {

            return super.selectList(JdbcUtil.getConnection(), BASE_QUERY_BY_ID, queryCode, stuId);
        }
        StringBuffer sb = new StringBuffer(BASE_QUERY_BY_ID + " and formId = " + formId[0]);

        for (int i = 1; i < formId.length; ++i) {
            sb.append(" OR formId = " + formId[i]);
        }

        System.out.println(sb.toString());

        return super.selectList(JdbcUtil.getConnection(), sb.toString(), queryCode, stuId);
    }

    public final List<Form> getList(int queryCode) {
        return super.selectList(JdbcUtil.getConnection(), BASE_QUERY, queryCode);
    }

    /**
     * 将数据库表中的过期的信息迁移到 oldform 中
     *
     * @return 返回操作是否成功
     */
    @Override
    public final Boolean updateTable() {
        return super.updateTable();
    }

    /**
     * @param targetPage 目标页面
     * @param size       记录条数
     * @return 返回对应的bean集合
     */
    @Override
    public List<Form> getPageList(int targetPage, int size) {
        return super.getPageList(BASE_PAGE_LIST, targetPage, size);
    }

    @Override
    public int getTotalCount() {
        String sql = "select count(*) from form";
        return super.getCount(JdbcUtil.getConnection(), sql);
    }

    /**
     * 获得分页查询的结果
     *
     * @param sql  需要执行的sql语句
     * @param args 需要传入的参数
     * @return 返回一个 bean集合
     * @deprecated
     */
    @Deprecated
    @Override
    public List<Form> getPageList(String sql, Object... args) {
        return super.getPageList(sql, args);
    }

    @Deprecated
    @Override
    public List<Form> getPageList(String sql, int targetPage, int size) {
        return super.getPageList(sql, targetPage, size);
    }
}
