package com.repairsys.service;

import com.repairsys.bean.vo.Result;

/**
 * @Author lyr
 * @create 2019/10/22 19:10
 */
public interface ExcelService {
    /**
     * 导出工人每天的工作任务 ，Excel表
     * @return 返回操作是否成功
     */
    Result<Boolean> exportTable();
}
