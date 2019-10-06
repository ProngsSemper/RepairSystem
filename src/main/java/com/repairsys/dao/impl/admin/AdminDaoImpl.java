package com.repairsys.dao.impl.admin;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.AdminDao;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.PageDao;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.string.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 16:54
 */
public class AdminDaoImpl extends BaseDao<Admin> implements AdminDao, PageDao<Admin> {
    private static final Logger logger = LoggerFactory.getLogger(AdminDaoImpl.class);

    private static final AdminDaoImpl ADMIN_DAO;

    private static final String LOGIN_FOR_ADMIN = "select * from administrators where `adminId`=? and `adminPassword` =?";
    private static final String QUERY_ALL_ADMIN = "Select * from administrators";
    private static final String REGISTER = "insert into administrators (`adminId`, `adminName`, `adminPassword`, `adminMail`) values(?,?,?,?)";
    private static final String QUERY_ONE = "select * from administrators where `adminId` = ?";

    static {
        ADMIN_DAO = new AdminDaoImpl();
    }

    public static AdminDaoImpl getInstance() {
        return ADMIN_DAO;
    }

    private AdminDaoImpl() {
        super(Admin.class);
    }

    /**
     * 根据 id 获取管理员信息
     *
     * @param id 通过管理员的 id进行查询
     * @return res 返回 bean管理员对象
     */
    @Override
    public Admin getById(String id) {

        Connection conn = JdbcUtil.getConnection();
        return super.selectOne(conn, QUERY_ONE);
    }

    /**
     * 获取管理员集合
     *
     * @return 返回一个bean集合，里面是admin(管理员)的bean对象集合，如果有异常返回 null,需要对返回值进行判断是否为 null
     */
    @Override
    public List<Admin> getAdminInfoList() {

        Connection conn = JdbcUtil.getConnection();
        return super.selectList(conn, QUERY_ALL_ADMIN);
    }

    /**
     * 管理员登录操作
     *
     * @param id       管理员账号
     * @param password 管理员密码
     * @return 返回一个bean管理员对象，如果有异常返回null
     */
    @Override
    public Admin login(String id, String password) {
        Connection conn = JdbcUtil.getConnection();
        String pwd = StringUtils.getStringMd5(password);
        logger.info(id + pwd);
        return super.selectOne(conn, LOGIN_FOR_ADMIN, id, pwd);
    }

    /**
     * 申请注册管理员账户
     *
     * @param uId       管理员账号
     * @param uName     管理员姓名
     * @param uPassword 管理员密码
     * @return 返回一个布尔值，如果没有异常返回true，否则返回 false
     */
    @Override
    public boolean registerPlus(String uId, String uName, String uPassword) {
        String pwd = StringUtils.getStringMd5(uPassword);
        return this.register(uId, uName, pwd, "");
    }

    /**
     * 申请注册管理员账户
     *
     * @param uId       用户的 id
     * @param uName     用户名字
     * @param uPassword 用户密码
     * @param email     用户的邮箱地址
     * @return 返回一个布尔值，如果没有异常返回true，否则返回 false
     */
    @Override
    public boolean registerPlus(String uId, String uName, String uPassword, String email) {
        String pwd = StringUtils.getStringMd5(uPassword);
        return this.register(uId, uName, pwd, email);
    }

    /**
     * 申请注册管理员账户
     *
     * @param args 用户传入的要插入的数据库的字段的参数
     * @return 返回一个布尔值，如果没有异常返回true，否则返回 false
     * @see AdminDaoImpl#registerPlus(String, String, String, String)    如果您是该项目的程序员，请使用registerPlus方法，该方法使用了 md5加密处理，这是专门针对管理员密码加密注册用的
     * @see AdminDaoImpl#registerPlus(String, String, String)    如果您是该项目的程序员，请使用registerPlus方法，该方法使用了 md5加密处理，这是专门针对管理员密码加密注册用的
     * @deprecated 该方法没有进行对特殊字段进行加密处理, 请不要直接使用，推荐使用 registerPlus方法，为管理员账户专门设计的
     */

    @Override
    public boolean register(Object... args) {

        Connection conn = JdbcUtil.getConnection();

        boolean b = super.addOne(conn, REGISTER, args);

        return b;
    }

    /**
     * 管理员发送维修通知
     *
     * @param stuMail 被通知学生的邮箱账号
     * @param day     师傅上门时间是本月的几号
     * @param hour    师傅上门时间是几点
     * @return 发送成功返回true
     * @throws Exception 抛出异常
     */
    @Override
    public boolean sendMail(String stuMail, int day, int hour) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        java.util.Date time = calendar.getTime();
        SimpleDateFormat df = new SimpleDateFormat("MM月dd日 HH时");
        String format = df.format(time);
        return MailUtil.sendMail(stuMail, format);
    }

    /**
     * 查询一个工人的所有表单记录
     *
     * @param workerName 查询的工人名字
     * @param page       第几页
     * @param size       查询几条记录
     * @return
     */
    @Override
    public List<Form> queryFormListByWorkerName(String workerName, int page, int size) {
        Worker worker = WorkerDaoImpl.getInstance().getWorkerKeyByName(workerName);

        return FormListDaoImpl.getInstance().queryAllFormIdByWorkerKey(worker.getwKey(), page, size);
    }













    /*
     *
     *
     * 下面是对于分页查询接口代码的实现
     *
     * */
    /**
     * 查询管理员的记录
     */
    private static final String QUERY_ADMIN_LIST = "select * from administrators limit ?,?";
    /**
     * 查询管理员条数记录
     */
    private static final String QUERY_ADMIN_LIST_COUNT = "select count(*) from administrators";

    /**
     * 查询出对应的数据库表信息
     *
     * @param targetPage 查询目标页面
     * @param size       查询的页面有多少条记录
     * @return 返回一个 java bean集合
     */
    @Override
    public List<Admin> selectPageList(int targetPage, int size) {
        int[] ans = EasyTool.getLimitNumber(targetPage, size);
        return super.selectList(JdbcUtil.getConnection(), QUERY_ADMIN_LIST, ans[0], ans[1]);
    }

    /**
     * 返回一共有多少条数据再数据库记录着
     *
     * @return int
     */
    @Override
    public int selectPageCount() {
        return super.getCount(JdbcUtil.getConnection(), QUERY_ADMIN_LIST_COUNT);
    }

    /**
     * 获得数据库满足某个条件的记录
     *
     * @return 返回的查询到的记录数
     */

    public int getCount() {
        return this.selectPageCount();
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
        return null;
    }

    /**
     * 返回一共有多少条数据再数据库记录着
     *
     * @param sql 要传入的sql语句
     * @return int
     */
    @Override
    public int selectPageCount(String sql) {
        return this.getCount();
    }
}
