package com.repairsys.dao;

import com.repairsys.bean.entity.WTime;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/8 13:03
 * <p>
 * 主要是记录着工人一天 8小时的任务清单，任务单的目的用于实现极其简单的推荐算法
 */
public interface TableListDao {
    /**
     * 获得工人的表单集合
     *
     * @return 获得工人表单集合
     */
    List<WTime> getTableList();

    /**
     * 管理员调用此方法，即可更新数据库表，不需要用别的什么方法了
     *
     * @return 返回更新的结果是否成功
     */
    boolean updateAll();

}
