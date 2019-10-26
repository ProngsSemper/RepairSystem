package com.repairsys.dao.impl.form;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.PageDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;

import java.sql.Connection;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/30 18:50
 */
public final class FormListDaoImpl extends FormDaoImpl implements PageDao<List<Form>> {
    private final Connection connection = JdbcUtil.getConnection();
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
    private static final String GET_OLD_BY_STUDENTID_COUNT = "select count(*) from oldform where stuId =?";
    private static final String SELECT_OLD_LIST_BY_STUID = "select * from oldform where stuId like '%";
    /**
     * 工具工人的名字，模糊查询出他们的维修表单
     */
    private static final String FUZZY_SEARCH_WORKER_FORM_LIST = "select * from form f where f.wKey in (select w.wkey from workers w where w.wName like '%";
    /**
     * 估计工人的名字，模糊查询出所有维修单记录
     */
    private static final String FUZZY_ALL_FORM = "select * from form f  where f.wKey in (select w.wkey from workers w where w.wName like '%rep%')" +

            "union select * from oldform o where o.wKey in(select w.wkey from workers w where w.wName like '%rep%') limit ?,?";

    private static final String ADMIN_INCOMPLETE_FORM = "select * from form where queryCode = 0 limit ?,?";
    private static final String ADMIN_COMPLETE_FORM = "select * from form where queryCode <> 0 UNION select * from oldform where queryCode <>0 limit ?,?";
    private static final String WORKER_INCOMPLETE_FORM = "select * from form where wKey = ? and queryCode = 1 limit ?,?";
    private static final String WORKER_COMPLETE_FORM = "SELECT * FROM form WHERE wKey = ? AND queryCode > 1 UNION SELECT * FROM oldform WHERE wKey = ? and queryCode > 1 limit ?,?";
    private static final String ADMIN_QUERY_TYPE = "SELECT * FROM `form` WHERE wType=? UNION SELECT * FROM `oldform` WHERE wType=? limit ?,?";
    private static final String QUERY_LEVEL = "SELECT * FROM `form` WHERE LEVEL=\"A\"";
    private static final String STUDENT_UNDONE = "SELECT * FROM `form` WHERE stuId=? limit ?,?";
    /**
     * 获取总页数
     */
    private static final String COUNT_SQL = "select form1.cnt+form2.cnt from (select count(*) cnt from form where) form1,(select count(*) cnt from oldform where) form2";

    private static final FormListDaoImpl DAO = new FormListDaoImpl();

    private FormListDaoImpl() {
        super();
    }

    public static FormListDaoImpl getInstance() {
        return DAO;
    }

    public final List<Form> getListById(String stuId, int queryCode, String... formId) {
        if (formId.length == 0) {

            return super.selectList(connection, BASE_QUERY_BY_ID, queryCode, stuId);
        }
        StringBuffer sb = new StringBuffer(BASE_QUERY_BY_ID + " and formId = " + formId[0]);

        for (int i = 1; i < formId.length; ++i) {
            sb.append(" OR formId = " + formId[i]);
        }

        System.out.println(sb.toString());

        return super.selectList(connection, sb.toString(), queryCode, stuId);
    }

    public final List<Form> getList(int queryCode) {
        return super.selectList(connection, BASE_QUERY, queryCode);
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
        return super.getCount(connection, sql);
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
    public List<Form> queryAllFormIdByWorkerKey(int wKey, int page, int size) {
        // WorkerDaoImpl.getInstance().
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, SEARCH_WKEY_FORM_LIST, wKey, wKey, ans[0], ans[1]);
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

        return super.selectList(connection, sql, ans[0], ans[1]);
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
        return super.getCount(connection, sql);
    }

    public List<Form> getPageByStudentId(String stuId, int page, int limit) {
        String finalSql = GET_FORM_BY_STUDENT_ID + stuId + "%'" + " limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, finalSql, stuId, ans[0], ans[1]);
    }

    private static final String GET_STU_ID_COUNT = "select count(*) from form where stuId =?";

    public int getPageCountByStudentId(String studentId) {
        return super.getCount(connection, GET_STU_ID_COUNT, studentId);

    }

    public List<Form> getOldPageListByStudentId(String studentId, int page, int limit) {

        String sql2 = SELECT_OLD_LIST_BY_STUID + studentId + "%' limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, sql2, ans[0], ans[1]);
    }

    public int getOldCountByStudentId(String studentId) {
        return super.getCount(connection, GET_OLD_BY_STUDENTID_COUNT, studentId);
    }

    private static final String GET_ALL_BY_STUDENT_NAME = "select * from oldform where stuId =? limit ?,?";

    public List<Form> getAllListByStudentId(String studentId, int page, int limit) {
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, GET_ALL_BY_STUDENT_NAME, studentId, ans[0], ans[1]);
    }

    public List<Form> workerGetAllListByStudentName(String studentName, int page, int limit) {
        String getAllByStudentName = "select * from form where stuName like '%" + studentName + "%' and queryCode=1 limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, getAllByStudentName, ans[0], ans[1]);
    }

    public List<Form> adminGetAllIncompleteListByStudentName(String studentName, int page, int limit) {
        String getAllByStudentName = "select * from form where stuName like '%" + studentName + "%' and queryCode=0 limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, getAllByStudentName, ans[0], ans[1]);
    }

    public List<Form> adminGetAllCompleteListByStudentName(String studentName, int page, int limit) {
        String getAllByStudentName = "select * from form where stuName like '%" + studentName + "%' and queryCode <> 0 UNION select * from oldform where stuName like '%" + studentName + "%' and queryCode <> 0 limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, limit);
        return super.selectList(connection, getAllByStudentName, ans[0], ans[1]);
    }

    public int getAllAdminIncompleteCountByStudentName(String studentName) {
        String sql = "select form1.cnt from (select count(*) cnt from form where stuName like '%" + studentName + "%' AND queryCode = 0) form1";
        return super.getCount(connection, sql);
    }

    public int getAllAdminCompleteCountByStudentName(String studentName) {
        String rex = " where stuName like '%" + studentName + "%' AND queryCode <> 0";
        return super.getCount(connection, COUNT_SQL.replaceAll("where", rex));
    }

    public int getAllCountByStudentId(String studentId) {
        String rex = " where stuId = ?";
        return super.getCount(connection, COUNT_SQL.replaceAll("where", rex), studentId);

    }

    public List<Form> batchSearchFormByWorkerName(String workerName, int page, int size) {
        String sql = FUZZY_SEARCH_WORKER_FORM_LIST + workerName + "%' ) limit ?,?";
        int[] ans = EasyTool.getLimitNumber(page, size);

        return super.selectList(connection, sql, ans[0], ans[1]);
    }

    public List<Form> batchSearchAllFormByWorkerName(String workerName, int page, int size) {
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, FUZZY_ALL_FORM.replaceAll("rep", workerName), ans[0], ans[1]);
    }

    private static final String GET_BY_ADMIN_KEY = "select adminKey from administrators where adminId = ?";

    public int getAdminKeyById(String adminId) {
        return super.selectOne(connection, GET_BY_ADMIN_KEY, adminId).getAdminKey();
    }

    private static final String GET_BY_WID = "select wKey from workers where wId = ?";

    public int getWorkerKeyById(String wId) {
        return super.selectOne(connection, GET_BY_WID, wId).getwKey();
    }

    public List<Form> adminIncompleteForm(int page, int size) {
        int[] ans = EasyTool.getLimitNumber(page, size);
        String sql = ADMIN_INCOMPLETE_FORM;
        if (!(super.selectList(connection, QUERY_LEVEL).isEmpty())) {
            String rex = "ORDER BY LEVEL limit";
            sql = sql.replaceAll("limit", rex);
        }
        return super.selectList(connection, sql, ans[0], ans[1]);
    }

    public List<Form> adminCompleteForm(int page, int size) {
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, ADMIN_COMPLETE_FORM, ans[0], ans[1]);
    }

    public List<Form> workerIncompleteForm(String wId, int page, int size) {
        int wKey = getWorkerKeyById(wId);
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, WORKER_INCOMPLETE_FORM, wKey, ans[0], ans[1]);
    }

    public List<Form> workerCompleteForm(String wId, int page, int size) {
        int wKey = getWorkerKeyById(wId);
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, WORKER_COMPLETE_FORM, wKey, wKey, ans[0], ans[1]);
    }

    public List<Form> adminQueryWorkerType(String wType, int page, int size) {
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, ADMIN_QUERY_TYPE, wType, wType, ans[0], ans[1]);
    }

    public List<Form> studentUndoneForm(String stuId, int page, int size) {
        int[] ans = EasyTool.getLimitNumber(page, size);
        return super.selectList(connection, STUDENT_UNDONE, stuId, ans[0], ans[1]);
    }

}
