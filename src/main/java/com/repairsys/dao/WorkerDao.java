package com.repairsys.dao;

import com.repairsys.bean.entity.Worker;

/**
 * @Author lyr
 * @create 2019/9/24 13:35
 */
public interface WorkerDao {

    /**
     *
     * 工人登录验证
     * @param wId 工人注册的账号
     * @param wPassword 工人注册传入的密码
     * @return 返回工人的 bean对象
     */
    Worker login(String wId,String wPassword);

    /**
     * 工人完成了修理任务
     * @param formId  工人完成了维修任务，可以修改表单的信息， 修改表单的信息需要填写 formId,指定具体的记录
     * @return 工人修改表单是否成功，如果有异常返回false，否则返回true
     */
    boolean completed(String formId);

    /**
     * 工人注册账号
     * @param person  注册工人信息到数据库，传入bean对象来读入数据库
     * @return 工人注册是否成功，出现异常则提交失败
     * @deprecated 暂时还没有用处
     */
    boolean register(Worker person);


    /**
     * 工人注册账号
     * @param args 要插入数据库记录传入的参数
     * @return 工人注册是否成功
     * @deprecated 在不确定该函数使用的 sql语句情况下，不要直接使用
     */
    boolean register(Object... args);

    /**
     * 工人注册时，写入数据库的一条记录
     * @param wId 工人的登录账号
     * @param wName 工人的名字
     * @param wTel 工人的电话号码
     * @param wPassword 工人的账号密码
     * @param mail 工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false
     */
    boolean register(String wId,String wName,String wTel,String wPassword,String mail);






}
