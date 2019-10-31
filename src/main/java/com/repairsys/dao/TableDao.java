package com.repairsys.dao;

import com.repairsys.bean.entity.ExcelTable;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/22 18:38
 */
public abstract class TableDao extends BaseDao<ExcelTable> {

    protected TableDao() {
        super(ExcelTable.class);
    }

    /**
     * 管理员导出 工人工作任务的 Excel表
     * @return 返回 Excel表的数据
     */
    public abstract List<ExcelTable> getTable();

}
