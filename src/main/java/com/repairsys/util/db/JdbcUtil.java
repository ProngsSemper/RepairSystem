package com.repairsys.util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.LinkedList;

/**
 * @author lyr
 * <p>
 * DAO层访问数据库需要用此得到连接
 */
public final class JdbcUtil {

    private static DataSource ds;

    static {
        ds = new ComboPooledDataSource("mysql");

    }

    /**
     * @return Connection
     * <p>
     * 获取数据库c3p0连接
     */
    public static Connection getConnection() {
        Connection conn = null;
        try {
            conn = ds.getConnection();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return conn;

    }

    /**
     * java.sql.Date 类型没有无参构造，因此没办法用 QueryRunner了,业务需求，因此还是需要有这样一个函数
     *
     * @param sql 传入的参数
     * @return 返回带有date 类型的链表
     */
    public static LinkedList<Date> getDateList(String sql) {
        java.sql.Date date = new Date(System.currentTimeMillis());
        Connection conn = JdbcUtil.getConnection();
        PreparedStatement psmt = null;
        ResultSet rs = null;
        LinkedList<Date> ans = null;

        try {
            psmt = conn.prepareStatement(sql);
            rs = psmt.executeQuery();
            // Date[] dList = new Date[2];int r=0;
            ans = new LinkedList<>();
            while (rs.next()) {
                ans.add((Date) rs.getObject(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {

            if (psmt != null) {
                try {
                    psmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return ans;
    }
}
