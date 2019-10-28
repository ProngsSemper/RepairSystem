package com.repairsys.dao.impl.worker;

import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.md5.Md5Util;

import java.sql.Connection;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/27 10:53
 */
public class WorkerDaoImpl extends BaseDao<Worker> implements com.repairsys.dao.WorkerDao {
    private final Connection connection = JdbcUtil.getConnection();

    private static final WorkerDaoImpl WORKER_DAO = new WorkerDaoImpl();
    private static final String WORKER_REGISTER = "insert into workers (wId,wName,wTel,wPassword,wMail)values(?,?,?,?,?)";
    private static final String WORKER_LOGIN = "select * from workers where wId = ? and wPassword = ?";
    private static final String SEARCH_WORKERS = "select * from workers where wName like '%";
    private static final String GET_WORKER = "select wKey from workers where wName = ?";
    private static final String GET_WORKER_KEY_BY_ID = "select wKey from workers where wId = ?";
    private static final String GET_WORKER_NAME_BY_ID = "select wName from workers where wId = ?";
    private static final String GET_WORKER_COUNT = "select count(*) from workers where wName = ?";
    private static final String GET_WORKER_TEL = "select wTel from workers where wKey = ?";
    private static final String UPDATE_QUERYCODE = "update form set queryCode = ? where formId = ?";
    private static final String GET_SUM = "select count(*) from workers";
    private static final String GET_WORKER_LIST = "select * from workers";
    private static final String SELECT_WORKER = "SELECT * FROM workers WHERE wKey=?";
    private static final String GET_TOKEN = "select wToken from workers where wId=?";
    private static final String TOKEN = "update workers set wToken=? where wId=?";

    public static WorkerDaoImpl getInstance() {
        return WORKER_DAO;
    }

    private WorkerDaoImpl() {
        super(Worker.class);
    }

    /**
     * 工人登录验证
     *
     * @param wId
     * @param wPassword
     * @return 返回工人 的 bean对象
     */
    @Override
    public Worker login(String wId, String wPassword) {
        String wToken = Md5Util.getMd5(String.valueOf(System.currentTimeMillis()));
        super.updateOne(connection, TOKEN, wToken, wId);
        return super.selectOne(connection, WORKER_LOGIN, wId, wPassword);
    }

    @Override
    public Worker getToken(String id) {
        return super.selectOne(connection, GET_TOKEN, id);
    }

    public Worker existToken(String token) {
        String sql = "select * from workers where wToken =?";
        return super.selectOne(connection, sql, token);
    }

    /**
     * 工人完成了修理任务
     *
     * @param queryCode 表单状态码
     * @param formId    表单id
     * @return 工人完成维修任务，维修单确认已经完成
     */
    @Override
    public boolean updateQueryCode(int queryCode, int formId) {
        return super.updateOne(connection, UPDATE_QUERYCODE, queryCode, formId);
    }

    /**
     * 工人注册账号
     *
     * @param person 工人注册时，需要填写的信息
     * @return
     */
    @Deprecated
    @Override
    public boolean register(Worker person) {
        return false;
    }

    /**
     * 工人注册账号
     *
     * @param args 要插入数据库记录传入的参数
     * @return 工人注册是否成功
     * @deprecated 在不确定该函数使用的 sql语句情况下，不要直接使用
     */
    @Deprecated
    @Override
    public boolean register(Object... args) {
        return false;
    }

    /**
     * 工人注册时，写入数据库的一条记录
     *
     * @param wId       工人的登录账号
     * @param wName     工人的名字
     * @param wTel      工人的电话号码
     * @param wPassword 工人的账号密码
     * @param wMail     工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false
     */
    @Override
    public boolean register(String wId, String wName, String wTel, String wPassword, String wMail) {
        return super.addOne(connection, WORKER_REGISTER, wId, wName, wTel, wPassword, wMail);
    }

    /**
     * 估计工人的名字进行模糊查询
     *
     * @param name 工人的名字
     * @return 返回可能要查找的工人信息
     */
    @Override
    public List<Worker> fuzzySearchWorkers(String name) {
        String finalSql = SEARCH_WORKERS + name + "%'";
        return super.selectList(connection, finalSql);
    }

    public List<Worker> fuzzySearchWorkers(String name, int targetPage, int size) {
        String finalSql = SEARCH_WORKERS + name + "%'" + " limit ?,?";
        int[] ans = EasyTool.getLimitNumber(targetPage, size);
        return super.selectList(connection, finalSql, ans[0], ans[1]);
    }

    public int fuzzySearchWorkersCount(String name) {
        return super.getCount(connection, GET_WORKER_COUNT);
    }

    /**
     * 输入工人的名字查询工人维修的单号
     *
     * @param workerName 工人的名字
     * @return 返回工人维修的单号
     */
    @Override

    public Worker getWorkerKeyByName(String workerName) {
        return super.selectOne(connection, GET_WORKER, workerName);
    }

    @Override
    public Worker getWorkerKeyById(String workerId) {
        return super.selectOne(connection, GET_WORKER_KEY_BY_ID, workerId);
    }

    public Worker getWorkerNameById(String workerId) {
        return super.selectOne(connection, GET_WORKER_NAME_BY_ID, workerId);
    }

    @Override
    public Worker getWorkerTelByKey(int wKey) {
        return super.selectOne(connection, GET_WORKER_TEL, wKey);
    }

    public int getTotalCount() {
        return super.getCount(connection, GET_SUM);
    }

    public List<Worker> getAllWorkerList() {
        return super.selectList(connection, GET_WORKER_LIST);
    }

    private static final String CNT_SQL = "SELECT COUNT(*) FROM form WHERE wKey = ? AND queryCode=1";

    @Override
    public int getAllIncompleteCountBywKey(int wKey) {
        return super.getCount(connection, CNT_SQL, wKey);

    }

    @Override
    public int getAllCompleteCountBywKey(int wKey) {
        String cntSql = "select form1.cnt+form2.cnt from (select count(*) cnt from form where) form1,(select count(*) cnt from oldform where) form2";
        String rex = " where wKey=? AND queryCode>1";
        return super.getCount(connection, cntSql.replaceAll("where", rex), wKey, wKey);

    }

    public List<Worker> getList(String sql, Object... obj) {
        return super.selectList(connection, sql, obj);
    }

    @Override
    public String getEvaluation(int wKey) {
        Worker worker = super.selectOne(connection, SELECT_WORKER, wKey);
        double good = worker.getGood();
        double mid = worker.getMid();
        double bad = worker.getBad();
        if (good == 0 && mid == 0 && bad == 0) {
            return "暂无评价！";
        }
        double sum = good + mid + bad;
        double pre = (good / sum) * 100;
        String result = String.format("%.1f", pre);
        return result + "%";
    }
}
