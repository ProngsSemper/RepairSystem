package com.repairsys.dao;

import com.repairsys.bean.entity.Evaluation;

import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/23 19:39
 */
public interface EvaluationDao {
    /**
     * 学生对工人评价
     *
     * @param msg  评价
     * @param wKey 工人key
     * @return 布尔值
     */
    Boolean addEvaluation(String msg, int wKey);

    /**
     * 工人查询学生评价
     *
     * @param wKey 工人key
     * @return 评价列表
     */
    List<Evaluation> getMsg(int wKey);
}
