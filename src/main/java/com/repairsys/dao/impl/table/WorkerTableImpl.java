package com.repairsys.dao.impl.table;

import com.repairsys.bean.entity.ExcelTable;
import com.repairsys.dao.TableDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/22 18:37
 */
public class WorkerTableImpl extends TableDao {
    private static final TableDao DAO = new WorkerTableImpl();

    public static TableDao getInstance() {
        return DAO;
    }

    private WorkerTableImpl() {
    }

    @Override
    public List<ExcelTable> getTable() {
        return super.selectList(JdbcUtil.getConnection(), ExcelTable.sql);
    }
}
