package com.repairsys.dao;

import com.repairsys.bean.entity.ExcelTable;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/22 18:38
 */
public abstract class TableDao extends BaseDao<ExcelTable> {

    protected TableDao() {
        super(ExcelTable.class);
    }


    public abstract List<ExcelTable> getTable();

}
