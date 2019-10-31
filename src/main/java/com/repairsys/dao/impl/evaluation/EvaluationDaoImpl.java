package com.repairsys.dao.impl.evaluation;

import com.repairsys.bean.entity.Evaluation;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.EvaluationDao;
import com.repairsys.util.db.JdbcUtil;

import java.sql.Connection;
import java.util.List;

/**
 * @author Prongs
 * @date 2019/10/23 19:41
 */
public class EvaluationDaoImpl extends BaseDao<Evaluation> implements EvaluationDao {

    private static final String ADD_EVALUATION = "insert into evaluation (msg,wKey) values (?,?)";
    private static final String GET_EVALUATION = "SELECT msg FROM `evaluation` WHERE wKey = ?";

    private static final EvaluationDaoImpl EVALUATION_DAO;

    static {
        EVALUATION_DAO = new EvaluationDaoImpl();
    }

    public static EvaluationDaoImpl getInstance() {
        return EVALUATION_DAO;
    }

    private EvaluationDaoImpl() {
        super(Evaluation.class);
    }

    @Override
    public Boolean addEvaluation(String msg, int wKey) {
        if ("".equals(msg) || msg == null || wKey == 0) {
            return false;
        }
        return super.addOne(JdbcUtil.getConnection(), ADD_EVALUATION, msg, wKey);
    }

    @Override
    public List<Evaluation> getMsg(int wKey) {
        return super.selectList(JdbcUtil.getConnection(), GET_EVALUATION, wKey);
    }
}
