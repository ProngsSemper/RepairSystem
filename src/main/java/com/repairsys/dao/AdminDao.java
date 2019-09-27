package com.repairsys.dao;

import com.repairsys.bean.entity.Admin;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/24 13:14
 */
public interface AdminDao {


    /**
     *
     * 根据 id 获取管理员信息
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
     * @param id 管理员账号
     * @param password 管理员密码
     * @return
     */
    Admin login(String id,String password);

    /**
     * 申请注册管理员账户
     * @param args 传入的数据库表的 列属性
     * @return
     */
    boolean register(Object... args);















}
