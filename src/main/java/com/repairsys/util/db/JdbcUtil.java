package com.repairsys.util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.sql.*;

/**
 * @author 林洋锐，Prongs
 * <p>
 * DAO层访问数据库需要用此得到连接
 */
public class JdbcUtil {

    private static DataSource ds;
    public static PreparedStatement pstmt = null;

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
     * 仅在工具类中使用的方法
     *
     * @param sql    要执行的sql语句
     * @param params 表中某对象的参数值 例：student.getS_name,student.getS_no 第0个参数为学生姓名
     * @return 返回预编译的PreparedStatement对象
     * @throws SQLException          数据库访问错误时抛出
     * @throws PropertyVetoException 当对属性的建议更改表示不可接受的值时，将抛出PropertyVetoException
     */
    private static PreparedStatement creatPreparedStatement(String sql, Object[] params) throws SQLException, PropertyVetoException {
        pstmt = getConnection().prepareStatement(sql);
        if (params != null) {
            /*
            获取所有传入的参数，并给sql语句中第i个参数赋值
             */
            for (int i = 0; i < params.length; i++) {
                pstmt.setObject(i + 1, params[i]);
            }
        }
        return pstmt;
    }

    public static void closeAll(ResultSet rs, PreparedStatement pstmt, Connection connection) {
        try {
            if (rs != null) {
                rs.close();
            }
            if (pstmt != null) {
                pstmt.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
