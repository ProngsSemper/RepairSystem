package com.repairsys.service;


import com.repairsys.bean.vo.Result;

import java.util.HashMap;

/**
 * @Author lyr
 * @create 2019/10/22 19:10
 */
public interface ExcelService {
    /**
     * 导出工人每天的工作任务 ，Excel表
     * @param res 需要填写的答案，添加数据后返回
     * @return 返回操作是否成功
     */
    Result<HashMap> exportTable(Result res);


    /**
     * @param res
     * @return
     */
    Result<HashMap> exportOneByOne(Result res);
}
