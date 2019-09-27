package com.repairsys.service;

import com.repairsys.service.impl.AdminServiceImpl;
import com.repairsys.service.impl.StudentServiceImpl;

/**
 * @author lyr, Prongs
 * @date 2019/9/21
 * <p>
 * service 层，保留一个接口，让控制层直接调用
 */
public class ServiceFactory {
    private static final AdminService ADMIN_SERVICE;
    private static final StudentService STUDENT_SERVICE;

    static {
        ADMIN_SERVICE = new AdminServiceImpl();
        STUDENT_SERVICE = new StudentServiceImpl();
    }

    public static StudentService getStudentService(){
        return ServiceFactory.STUDENT_SERVICE;
    }
}
