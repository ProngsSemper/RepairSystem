package com.repairsys.dao.impl.worker;

import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/27 10:52
 */
public class WorkerDaoImpl extends BaseDao<Worker> implements com.repairsys.dao.WorkerDao {
    private static final WorkerDaoImpl WORKER_DAO = new WorkerDaoImpl();
    private static final String WORKER_REGISTER = "insert into workers (wId,wName,wTel,wPassword,wMail)values(?,?,?,?,?)";
    private static final String WORKER_LOGIN = "select * from workers where wId = ? and wPassword = ?";
    private static final String SEARCH_WORKERS = "select * from workers where wName like '%";
    private static final String GET_WORKER = "select wKey from workers where wName = ?";
    private static final String GET_WORKER_COUNT = "select count(*) from workers where wName = ?";
    private static final String GET_WORKER_TEL = "select wTel from workers where wKey = ?";
    private static final String UPDATE_QUERYCODE = "update form set queryCode = ? where formId = ?";

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
        return super.selectOne(JdbcUtil.getConnection(), WORKER_LOGIN, wId, wPassword);
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
        return super.updateOne(JdbcUtil.getConnection(), UPDATE_QUERYCODE, queryCode, formId);
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
        return super.addOne(JdbcUtil.getConnection(), WORKER_REGISTER, wId, wName, wTel, wPassword, wMail);
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
        return super.selectList(JdbcUtil.getConnection(), finalSql);
    }

    public List<Worker> fuzzySearchWorkers(String name, int targetPage, int size) {
        String finalSql = SEARCH_WORKERS + name + "%'" + " limit ?,?";
        int[] ans = EasyTool.getLimitNumber(targetPage, size);
        return super.selectList(JdbcUtil.getConnection(), finalSql, ans[0], ans[1]);
    }

    public int fuzzySearchWorkersCount(String name) {
        return super.getCount(JdbcUtil.getConnection(), GET_WORKER_COUNT);
    }

    /**
     * 输入工人的名字查询工人维修的单号
     *
     * @param workerName 工人的名字
     * @return 返回工人维修的单号
     */
    @Override

    public Worker getWorkerKeyByName(String workerName) {
        return super.selectOne(JdbcUtil.getConnection(), GET_WORKER, workerName);
    }

    @Override
    public Worker getWorkerTelByKey(int wKey){
        return super.selectOne(JdbcUtil.getConnection(), GET_WORKER_TEL, wKey);
    }

}
