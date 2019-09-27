package com.repairsys.dao;

import com.repairsys.util.db.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author lyr
 * @date 2019/9/21 -20:08
 * <p>
 * 常用的方法,查询单条，查询多条
 */
public abstract class BaseDao<T> {

    private QueryRunner queryRunner;
    private BeanHandler<T> beanHandler;
    private BeanListHandler<T> beanListHandler;

    protected BaseDao(Class<T> clazz) {
        if (clazz == null) {
            throw new IllegalArgumentException("传入参数为空");
        }

        queryRunner = new QueryRunner();

        /* 把结果集转为一个 Bean,并返回。 Bean的类型在创建BeanHandler 对象时以 Class 对象的方式传入 BeanHandler(Class<T> type)。 */
        beanHandler = new BeanHandler<>(clazz);

        /*  把结果集转为一个 Bean 的 List, 并返回.。Bean的类型在创建 BeanListHanlder对象时以 Class对象的方式传入，可以适应列的别名来映射 JavaBean 的属性 */
        beanListHandler = new BeanListHandler<>(clazz);
    }

    /**
     * 查询一条数据，封装为T 类型返回
     * @param con
     * @param sql
     * @param args
     * @return
     */
    protected T selectOne(Connection con, String sql, Object... args) {
        T res = null;


        try {
            res = queryRunner.query(con, sql, this.beanHandler, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return res;

    }

    /**
     * 查询一堆数据，封装为T类型的集合并返回
     * @param con
     * @param sql
     * @param args
     * @return
     */
    protected List<T> selectList(Connection con, String sql, Object... args)  {
        List<T> res = null;


        try {
            res = queryRunner.query(con, sql, beanListHandler, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return res;
    }

    /**
     * 添加一条为T类型的数据，并回馈结果
     * @param con
     * @param sql
     * @param args
     * @return
     */
    protected boolean addOne(Connection con,String sql,Object... args)
    {
        boolean res = false;

        try {
            queryRunner.insert(con,sql,beanHandler,args);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    /**
     * 删除一条为 T类型的数据，并回馈结果
     * @param con
     * @param sql
     * @return
     */
    protected boolean deleteOne(Connection con,String sql)
    {
        boolean res = false;
        try {
            queryRunner.update(con,sql);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 修改数据库的一条数据
     * @param con
     * @param sql
     * @param args
     * @return
     */
    protected boolean updateOne(Connection con,String sql,Object... args)
    {
        boolean res = false;
        try {
            queryRunner.update(con,sql,args);
            res = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;

    }

    protected Long getCount(Connection con,String sql,Object arg)
    {
        try {
            Long count = queryRunner.query(JdbcUtil.getConnection(),sql,new ScalarHandler<Long>(),arg);
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0L;
    }

}
