package com.repairsys.dao;

import com.repairsys.bean.entity.Form;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/3 16:11
 *
 *  实现分页查询的功能
 *
 *
 */
public interface PageDao<T> {

    /**
     * 查询出对应的数据库表信息
     * @param targetPage 查询目标页面
     * @param size 查询的页面有多少条记录
     * @return 返回一个 java bean集合
     *
     */
    List<T> selectPageList(int targetPage,int size);

    /**
     * 返回一共有多少条数据再数据库记录着
     * @return int
     *
     */
    int selectPageCount();

    /**
     * 查询出对应的数据库表信息
     * @param sql 查询的sql语句
     * @param targetPage  目标页面
     * @param size 分页记录
     * @return 返回一个bean集合
     */
    List<Form> selectPageList(String sql, int targetPage, int size);


    /**
     *
     * 返回一共有多少条数据再数据库记录着
     * @param sql 要传入的sql语句
     * @return int
     */
    int selectPageCount(String sql);





}
