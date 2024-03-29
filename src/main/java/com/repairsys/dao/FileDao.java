package com.repairsys.dao;

import com.repairsys.bean.entity.Photo;

import java.util.LinkedList;

/**
 * @Author lyr
 * @create 2019/10/24 0:09
 */
public interface FileDao<T> {

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
     *
     * @param id 数据库表的id字段
     * @return 返回要查询的javabean
     */
    T getBeanInfo(String id);

    /**
     * 添加一条数据进入数据库
     *
     * @param paths 存储的路径
     * @return 返回存储的主键
     */
    int addOne(LinkedList<String> paths);

    /**
     * 获取上传的图片路径
     *
     * @param formId 工具学生的表单获取图片路径
     * @return
     */
    public Photo getImgPath(String formId);

}
