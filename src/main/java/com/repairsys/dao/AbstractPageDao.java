package com.repairsys.dao;

import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.easy.EasyTool;

import java.sql.Connection;
import java.util.List;

/**
 * 实现分页查询的功能
 *
 * @Author lyr
 * @create 2019/10/1 20:52
 */
public abstract class AbstractPageDao<T> extends BaseDao<T> {
    private static final String SELECT_PAGE = "select * from ? limit ?,?";
    

    protected AbstractPageDao(Class<T> clazz) {
        super(clazz);
    }

    /**
     * 获得分页查询的结果
     *
     * @param sql  需要执行的sql语句
     * @param args 需要传入的参数
     * @return 返回一个 bean集合
     */
    public List<T> getPageList(String sql, Object... args) {

        return super.selectList(JdbcUtil.getConnection(), sql, args);
    }

    /**
     * @param sql        传入的sql语句
     * @param targetPage 目标页面
     * @param size       查询记录条数
     * @return 返回一个bean集合
     */
    public List<T> getPageList(String sql, int targetPage, int size) {
        int[] ans = EasyTool.getLimitNumber(targetPage, size);

        return super.selectList(JdbcUtil.getConnection(), sql, ans[0], ans[1]);
    }

    /**
     * 分页查询的功能
     *
     * @param targetPage 目标页面
     * @param size       记录条数
     * @return 一个bean集合
     */
    public abstract List<T> getPageList(int targetPage, int size);

}
