package com.repairsys.dao;

import com.repairsys.bean.entity.Worker;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/24 13:35
 */
public interface WorkerDao {

    /**
     * 工人登录验证
     *
     * @param wId       工人注册的账号
     * @param wPassword 工人注册传入的密码
     * @return 返回工人的 bean对象
     */
    Worker login(String wId, String wPassword);

    /**
     * 工人完成了修理任务
     * <p>
     * 定义queryCode ,0是开始状态，开始报修，1是联系状态，已经约好工人，2是修好7天内状态，
     * 3是已经修好状态（迁移入oldform)，-1是缺少材料等情况 还需要再修。
     *
     * @param queryCode 表单状态码
     * @param formId    工人完成了维修任务，可以修改表单的信息， 修改表单的信息需要填写 formId,指定具体的记录
     * @return 工人修改表单是否成功，如果有异常返回false，否则返回true
     */
    boolean updateQueryCode(int queryCode, int formId);

    /**
     * 工人注册账号
     *
     * @param person 注册工人信息到数据库，传入bean对象来读入数据库
     * @return 工人注册是否成功，出现异常则提交失败
     * @deprecated 暂时还没有用处
     */
    boolean register(Worker person);

    /**
     * 工人注册账号
     *
     * @param args 要插入数据库记录传入的参数
     * @return 工人注册是否成功
     * @deprecated 在不确定该函数使用的 sql语句情况下，不要直接使用
     */
    boolean register(Object... args);

    /**
     * 工人注册时，写入数据库的一条记录
     *
     * @param wId       工人的登录账号
     * @param wName     工人的名字
     * @param wTel      工人的电话号码
     * @param wPassword 工人的账号密码
     * @param mail      工人的电子邮箱号码
     * @return 注册成功返回true，若出现异常注册失败返回false
     */
    boolean register(String wId, String wName, String wTel, String wPassword, String mail);

    /**
     * 估计工人的名字进行模糊查询
     *
     * @param name 工人的名字
     * @return 返回可能要查找的工人信息
     */
    List<Worker> fuzzySearchWorkers(String name);

    /**
     * 输入工人的名字查询工人维修的单号
     *
     * @param workerName 工人的名字
     * @return 返回工人维修的单号
     */
    Worker getWorkerKeyByName(String workerName);

    /**
     * 主要用于工人修改报修单状态时给学生发送邮件，根据wKey获取工人电话
     *
     * @param wKey 工人Key
     * @return 返回工人的电话
     */
    Worker getWorkerTelByKey(String wKey);





}
