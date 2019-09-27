package com.repairsys.dao;

import com.repairsys.dao.impl.AdminDaoImpl;

/**
 * @Author lyr
 * @create 2019/9/24 17:48
 */
public class DaoFactory {
    /** 负责处理管理员的dao实现类 */
    private static AdminDaoImpl ADMIN_DAO;

    /** 负责处理维修表单的dao实现类 */
    private static FormDao formDaoDao;

    /** 负责处理学生信息的dao实现类 */
    private static StudentDao STUDENT_DAO;

    /** 负责处理工人信息的实现类 */
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
