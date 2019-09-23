package com.repairsys.dao;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 林洋锐，Prongs
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

    protected T selectOne(Connection con, String sql, Object... args) {
        T res = null;

        try {
            res = queryRunner.query(con, sql, this.beanHandler, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;

    }

    protected List<T> selectList(Connection con, String sql, Object... args) {
        List<T> res = null;

        try {
            res = queryRunner.query(con, sql, beanListHandler, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

}
