package com.repairsys.dao.impl.worker;

import com.repairsys.bean.entity.RecommendedWorker;
import com.repairsys.dao.BaseDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/11/16 14:40
 */
public class RecommendWorkerDaoImpl extends BaseDao<RecommendedWorker> {
    protected RecommendWorkerDaoImpl() {
        super(RecommendedWorker.class);
    }

    public static RecommendWorkerDaoImpl getInstance() {
        return DAO;
    }

    private static final RecommendWorkerDaoImpl DAO = new RecommendWorkerDaoImpl();

    public List<RecommendedWorker> getList(String sql, Object... args) {
        return super.selectList(JdbcUtil.getConnection(), sql, args)
                ;
    }
}
