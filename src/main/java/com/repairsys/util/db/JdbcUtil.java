package com.repairsys.util.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

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

}
