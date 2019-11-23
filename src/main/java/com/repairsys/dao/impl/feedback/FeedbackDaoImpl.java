package com.repairsys.dao.impl.feedback;

import com.repairsys.bean.entity.Feedback;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.FeedbackDao;
import com.repairsys.util.db.JdbcUtil;

/**
 * @author Prongs
 * @date 2019/10/30 10:57
 */
public class FeedbackDaoImpl extends BaseDao<Feedback> implements FeedbackDao {
    private static final FeedbackDaoImpl FEEDBACK_DAO;
    // private final Connection JdbcUtil.getConnection() = JdbcUtil.getConnection();

    private static final String CREATE_FEEDBACK = "insert into feedback (stuId,stuName,stuPhone,msg)values(?,?,?,?)";

    static {
        FEEDBACK_DAO = new FeedbackDaoImpl();
    }

    public static FeedbackDaoImpl getInstance() {
        return FEEDBACK_DAO;
    }

    private FeedbackDaoImpl() {
        super(Feedback.class);
    }

    @Override
    public void createFeedback(String stuId, String stuName, String stuPhone, String msg) {
        addOne(JdbcUtil.getConnection(), CREATE_FEEDBACK, stuId, stuName, stuPhone, msg);
    }
}
