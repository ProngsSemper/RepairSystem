package com.repairsys.dao.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.PageDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/30 18:50
 */
public final class FormListDaoImpl extends FormDaoImpl implements PageDao<List<Form>> {
    /**
     * 根据学生的id号码 和 需要查询的表单状态进行查询
     */
    private static final String BASE_QUERY_BY_ID = "select * from form where queryCode = ? and stuId = ?";
    private static final String BASE_QUERY = "select * from form where queryCode = ? ";
    private static final String BASE_PAGE_LIST = "select * from form  where queryCode <=0 limit ?,?";

    /**
     * 分页查询一个员工修理任务的表单信息
     */
    private static final String SEARCH_WKEY_FORM_LIST = "select * from form where wKey = ? union select * from oldform where wKey = ? limit ?,?";

    /**
     * 分页查询学生 id的前半段
     */
    private static final String GET_FORM_BY_STUDENT_ID = "select * from form where stuId like '%";
    private static final String GET_OLD_BY_STUDENTID_COUNT = "select count(*) from oldform where stuId like '%";
    private static final String SELECT_OLD_LIST_BY_STUID = "select * from oldform where stuId like '%";

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
        String sql = "select count(*) from form where queryCode<=0";
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

    /**
     * 分页查询出一个员工的所有维修记录
     *
     * @param wKey 员工的key，
     * @param page 查询的页面
     * @param size 查询的记录数
     * @return bean表单集合
     */
    @Override
    public List<Form> queryAllFormIdByWorkerKey(String wKey, int page, int size) {
        // WorkerDaoImpl.getInstance().
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(JdbcUtil.getConnection(), SEARCH_WKEY_FORM_LIST, wKey, wKey, ans[0], ans[1]);
    }

    /**
     * 返回工人维修表单的总数
     *
     * @param wkey 工人的账号
     * @return 返回工人维修过的表单的记录数
     */
    @Override
    public int getCountByWorkerKey(String wkey) {
        return super.getCountByWorkerKey(wkey);
    }

    /**
     * 查询出对应的数据库表信息
     *
     * @param targetPage 查询目标页面
     * @param size       查询的页面有多少条记录
     * @return 返回一个 java bean集合
     * @deprecated
     */
    @Override
    public List<List<Form>> selectPageList(int targetPage, int size) {
        return null;
    }

    /**
     * 返回一共有多少条数据再数据库记录着
     *
     * @return int
     * @deprecated
     */
    @Deprecated
    @Override
    public int selectPageCount() {
        return 0;
    }

    /**
     * 查询出对应的数据库表信息
     *
     * @param sql        查询的sql语句
     * @param targetPage 目标页面
     * @param size       分页记录
     * @return 返回一个bean集合
     */
    @Override
    public List<Form> selectPageList(String sql, int targetPage, int size) {
        int[] ans = EasyTool.getLimitNumber(targetPage, size);

        return super.selectList(JdbcUtil.getConnection(), sql, ans[0], ans[1]);
        // return null;
    }

    /**
     * 返回一共有多少条数据再数据库记录着
     *
     * @param sql 要传入的sql语句
     * @return int
     */
    @Override
    public int selectPageCount(String sql) {
        return super.getCount(JdbcUtil.getConnection(), sql);
    }

    public List<Form> getPageByStudentId(String stuId, int page, int limit) {
        String finalSql = GET_FORM_BY_STUDENT_ID + stuId + "%'" + " limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(JdbcUtil.getConnection(), finalSql, stuId, ans[0], ans[1]);
    }

    public int getPageCountByStudentId(String studentId) {
        String GET_STU_ID_COUNT = "select count(*) from form where stuId like '%" + studentId + "%'";
        return super.getCount(JdbcUtil.getConnection(), GET_STU_ID_COUNT);

    }

    public List<Form> getOldPageListByStudentId(String studentId, int page, int limit) {

        String sql2 = SELECT_OLD_LIST_BY_STUID + studentId + "%' limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(JdbcUtil.getConnection(), sql2, ans[0], ans[1]);
    }

    public int getOldCountByStudentId(String student) {
        String sql = GET_OLD_BY_STUDENTID_COUNT + student + "%'";
        return super.getCount(JdbcUtil.getConnection(), GET_OLD_BY_STUDENTID_COUNT);
    }

    public List<Form> getAllListByStudentId(String studentId, int page, int limit) {
        String GET_ALL_BY_STUDENT_ID = "select * from form where stuId like '%" + studentId + "%'"
                + " union select * from oldform where stuId like '%" + studentId + "%' limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(JdbcUtil.getConnection(), GET_ALL_BY_STUDENT_ID, ans[0], ans[1]);
    }

    public int getAllCountByStudentId(String studentId) {
        String countSql = "select form1.cnt+form2.cnt from (select count(*) cnt from form where) form1,(select count(*) cnt from oldform where) form2";
        String rex = " where stuId like '%" + studentId + "%'";

        return super.getCount(JdbcUtil.getConnection(), countSql.replaceAll("where", rex));

    }

}
