package com.repairsys.dao;

import javafx.concurrent.Worker;

/**
 * @Author lyr
 * @create 2019/9/24 13:35
 */
public interface WorkerDao {

    /**
     *
     * 工人登录验证
     * @param wId
     * @param wPassword
     * @return
     */
    Worker login(String wId,String wPassword);

    /**
     * 工人完成了修理任务
     * @param formId
     * @return
     */
    boolean completed(String formId);

    /**
     * 工人注册账号
     * @param person
     * @return
     */
    boolean register(Worker person);





}
