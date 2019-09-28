package com.repairsys.dao;

import com.repairsys.bean.entity.Form;
import com.repairsys.dao.impl.AdminDaoImpl;
import com.repairsys.dao.impl.FormDaoImpl;
import com.repairsys.dao.impl.StudentDaoImpl;

/**
 * @Author lyr
 * @create 2019/9/24 17:48
 */
public class DaoFactory {
    /**
     * 负责处理管理员的dao实现类
     */
    private static AdminDaoImpl ADMIN_DAO = AdminDaoImpl.getInstance();

    /**
     * 负责处理维修表单的dao实现类
     */
    private static FormDao formDaoDao = FormDaoImpl.getInstance();

    /**
     * 负责处理学生信息的dao实现类
     */
    private static StudentDao STUDENT_DAO = StudentDaoImpl.getInstance();

    /**
     * 负责处理工人信息的实现类
     */
    private static WorkerDao WORKER_DAO;

    public static AdminDao getAdminDao() {

        return ADMIN_DAO;
    }

    public static StudentDao getStudentDao() {


        return STUDENT_DAO;
    }
    public static FormDao getFormDao()
    {
        return formDaoDao;
    }

}
