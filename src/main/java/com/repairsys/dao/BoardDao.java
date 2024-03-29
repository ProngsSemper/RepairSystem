package com.repairsys.dao;

import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/9 10:34
 */
public interface BoardDao {
    /**
     * 查询所有历史公告
     *
     * @return 返回数据库中board表的数据
     */
    List getHistoryBoard();

    /**
     * 获取board表中一共有多少条数据
     *
     * @return board表中所有数据条数
     */
    int getAllCountInBoard();

    /**
     * 查询最新的学校公告 即queryCode=1的公告
     *
     * @return 返回一条最新的学校公告
     */
    List getBoard();
}
