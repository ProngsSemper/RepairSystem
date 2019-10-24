package com.repairsys.dao;

/**
 * @Author lyr
 * @create 2019/10/24 0:09
 */
public interface FileDao<T> {
    //TODO:还没搞定
    /**
     * 获取photo 表中的最大主键的下一位
     */
    String GET_IMG_ID = "select max(`photoId`)+1 from photo;";


    /**
     * @return 返回主键id
     */
    int getId();

    /**
     * 获取数据库中的一条记录
     * @param id 数据库表的id字段
     * @return 返回要查询的javabean
     */
    T getBeanInfo(String id);





}
