package com.repairsys.dao;

import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Form;

import java.sql.Timestamp;
import java.util.List;

/**
 * @Author lyr, Prongs
 * @create 2019/9/24 13:14
 */
public interface AdminDao {

    /**
     * 根据 id 获取管理员信息
     *
     * @param id
     * @return
     */
    Admin getById(String id);

    /**
     * 获取管理员集合
     *
     * @return
     */
    List<Admin> getAdminInfoList();

    /**
     * 管理员登录操作
     *
     * @param id       管理员账号
     * @param password 管理员密码
     * @return
     */
    Admin login(String id, String password);

    /**
     * 申请注册管理员账户
     *
     * @param args 传入的数据库表的 列属性
     * @return
     */
    boolean register(Object... args);

    /**
     * 添加了 md5加密的功能
     *
     * @param uId       用户id
     * @param uName     用户名字
     * @param uPassword 用户密码
     * @return 注册是否成功
     */
    boolean registerPlus(String uId, String uName, String uPassword);

    /**
     * 用户注册
     *
     * @param uId       用户id
     * @param uName     用户名字
     * @param uPassword 用户密码
     * @param email     用户的邮箱信息
     * @return 用户的注册是否成功
     */
    boolean registerPlus(String uId, String uName, String uPassword, String email);

    /**
     * 管理员发送维修通知
     *
     * @param stuMail 被通知学生的邮箱账号
     * @param day     师傅上门时间是本月的几号
     * @param hour    师傅上门时间是几点
     * @return 发送成功返回true
     * @throws Exception 抛出异常
     */
    boolean sendMail(String stuMail, int day, int hour) throws Exception;

    /**
     * 查询一个工人的所有表单记录
     *
     * @param workerName 查询的工人名字
     * @param page       第几页
     * @param size       查询几条记录
     * @return
     */
    List<Form> queryFormListByWorkerName(String workerName, int page, int size);

    /**
     * 获得数据库满足某个条件的记录
     *
     * @return 返回的查询到的记录数
     */
    int getCount();

    /**
     * 根据工人名字查询总数
     *
     * @param wName 工人方法
     * @return
     */
    int getAllCountByWorkerName(String wName);

    /**
     * 管理员发布公告
     * 自动设置新公告的queryCode为1，旧公告的queryCode为-1
     *
     * @param board       公告内容
     * @param releaseDate 发布时间 自动填写
     */
    void releaseBoard(String board, Timestamp releaseDate);

    /**
     * 根据管理员key查管理员未处理的报修单
     *
     * @return 返回未处理报修单总数
     */
    int getAllIncompleteCountByAdminKey();

    /**
     * 管理员根据工种类型查询报修单总数
     *
     * @param wType 工种类型
     * @return 返回报修单总数
     */
    int getAllCountByWorkerType(String wType);

    /**
     * 根据管理员key返回已处理报修单总数
     *
     * @return 返回总数
     */
    int getAllCompleteCountByAdminKey();

    /**
     * 根据管理员id查管理员key
     *
     * @param adminId 管理员id
     * @return 管理员对象
     */
    Admin queryKey(String adminId);
}
