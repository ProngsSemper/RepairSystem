package com.repairsys.dao.impl.file;

import com.repairsys.bean.entity.Photo;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.FileDao;
import com.repairsys.util.db.JdbcUtil;

/**
 * @Author lyr
 * @create 2019/10/24 13:32
 */
public class FileDaoImpl extends BaseDao<Photo> implements FileDao<Photo> {
    protected FileDaoImpl() {
        super(Photo.class);
    }

    /**
     * @return 返回主键id
     */
    @Override
    public int getId() {
        return 0;
    }

    private static final String GET_ONE_INFO = "select * from photo where photoId = ?";
    /**
     * 获取数据库中的一条记录
     *
     * @param id 数据库表的id字段
     * @return 返回要查询的javabean
     */
    @Override
    public Photo getBeanInfo(String id) {
        return super.selectOne(JdbcUtil.getConnection(),GET_ONE_INFO,id);
    }


}
