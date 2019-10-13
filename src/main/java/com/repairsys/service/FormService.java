package com.repairsys.service;

import com.repairsys.bean.vo.Result;

/**
 * @author Prongs
 * @date 2019/10/2 12:58
 */
public interface FormService {

    /**
     * 获取form表的总数
     *
     * @return 返回form表总数
     */
    int getTotalCount();

    /**
     * 分页显示
     *
     * @param targetPage 当前页
     * @param size       每页显示数据量
     * @return form表中的数据
     */
    Result getPageList(int targetPage, int size);
}
