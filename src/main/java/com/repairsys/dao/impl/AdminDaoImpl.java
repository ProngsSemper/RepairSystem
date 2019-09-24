package com.repairsys.dao.impl;

import com.repairsys.bean.entity.Admin;
import com.repairsys.dao.AdminDao;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 16:54
 */
public class AdminDaoImpl extends BaseDao<Admin> implements AdminDao {

    private static final AdminDaoImpl ADMIN_DAO;

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
     * @param id
     * @return
     */
    @Override
    public Admin getById(String id) {
        return null;
    }

    /**
     * 获取管理员集合
     *
     * @return
     */
    @Override
    public List<Admin> getAdminInfoList() {
        String sql = "Select * from administrators";
        Connection conn = JdbcUtil.getConnection();
        List<Admin> list = super.selectList(conn, sql);
        return list;
    }

    /**
     * 管理员登录操作
     *
     * @param id       管理员账号
     * @param password 管理员密码
     * @return
     */
    @Override
    public Admin login(String id, String password) {
        Connection conn = JdbcUtil.getConnection();
        String sql = "select * from administrators where adminId=? and adminPassword=?";
        Admin admin = super.selectOne(conn, sql, id, password);

        return admin;

    }

    /**
     * 申请注册管理员账户
     *
     * @param args
     * @return
     */
    @Override
    public boolean register(Object... args) {

        Connection conn = JdbcUtil.getConnection();
        String sql = "insert into administrators values(?,?,?)";
        boolean b = super.addOne(conn, sql, args);

        return b;
    }

}
