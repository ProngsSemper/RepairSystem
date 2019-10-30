package com.repairsys.dao;

import com.repairsys.dao.feedback.FeedbackDaoImpl;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.board.BoardDaoImpl;
import com.repairsys.dao.impl.evaluation.EvaluationDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 17:48
 */
public class DaoFactory {
    /**
     * 负责处理管理员的dao实现类
     */
    private static AdminDao ADMIN_DAO = AdminDaoImpl.getInstance();

    /**
     * 负责处理维修表单的dao实现类
     */
    private static FormDao formDaoDao = FormListDaoImpl.getInstance();

    /**
     * 负责处理工人信息的实现类
     */
    private static WorkerDao WORKER_DAO = WorkerDaoImpl.getInstance();

    /**
     * 负责处理公告信息的实现类
     */
    private static BoardDao BOARD_DAO = BoardDaoImpl.getInstance();
    /**
     * 负责处理学生对工人详细评价的实现类
     */
    private static EvaluationDao Evaluation_DAO = EvaluationDaoImpl.getInstance();
    /**
     * 负责处理学留言反馈的实现类
     */
    private static FeedbackDao Feedback_DAO = FeedbackDaoImpl.getInstance();

    public static AdminDao getAdminDao() {

        return ADMIN_DAO;
    }

    public static FormDao getFormDao() {
        return formDaoDao;
    }

    public static WorkerDao getWorkerDao() {
        return WORKER_DAO;
    }

    public static BoardDao getBoardDao() {
        return BOARD_DAO;
    }

    public static EvaluationDao getEvaluationDao() {
        return Evaluation_DAO;
    }

    public static FeedbackDao getFeedbackDao() {
        return Feedback_DAO;
    }

}
