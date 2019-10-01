package com.repairsys.dao;

import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.form.FormDao;
import com.repairsys.dao.impl.form.FormDaoImpl;
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
    private static FormDao formDaoDao = FormDaoImpl.getInstance();


    /**
     * 负责处理工人信息的实现类
     */
    private static WorkerDao WORKER_DAO = WorkerDaoImpl.getInstance();

    public static AdminDao getAdminDao() {

        return ADMIN_DAO;
    }

    public static FormDao getFormDao() {
        return formDaoDao;
    }

    public static WorkerDao getWorkerDao() {
        return WORKER_DAO;
    }

}
