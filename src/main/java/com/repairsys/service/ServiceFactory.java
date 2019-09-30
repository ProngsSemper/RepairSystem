package com.repairsys.service;

import com.repairsys.service.impl.admin.AdminServiceImpl;
import com.repairsys.service.impl.worker.WorkerServiceImpl;

/**
 * @author lyr, Prongs
 * @date 2019/9/21
 * <p>
 * service 层，保留一个接口，让控制层直接调用
 */
public class ServiceFactory {
    private static final AdminServiceImpl ADMIN_SERVICE;
    private static final WorkerServiceImpl WORKER_SERVICE;

    static {
        ADMIN_SERVICE = new AdminServiceImpl();
        WORKER_SERVICE = new WorkerServiceImpl();
    }

    public static AdminServiceImpl getAdminService() {
        return ServiceFactory.ADMIN_SERVICE;
    }


    public static WorkerServiceImpl getWorkerService() {
        return ServiceFactory.WORKER_SERVICE;
    }
}
