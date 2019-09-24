package com.repairsys.dao;

import com.repairsys.bean.entity.Student;
import com.repairsys.dao.impl.AdminDaoImpl;

/**
 * @Author lyr
 * @create 2019/9/24 17:48
 */
public class DaoFactory {
    private static AdminDao ADMIN_DAO;
    private static FormDao  FORM_DAO;
    private static StudentDao STUDENT_DAO;
    private static WorkerDao WORKER_DAO;

    public static AdminDao getAdminDao()
    {
        if(null==ADMIN_DAO)
        {
            ADMIN_DAO = AdminDaoImpl.getInstance();
        }
        return ADMIN_DAO;
    }





}
