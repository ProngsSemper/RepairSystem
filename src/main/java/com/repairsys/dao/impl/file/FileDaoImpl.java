package com.repairsys.dao.impl.file;

import com.repairsys.bean.entity.Photo;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.FileDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.LinkedList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Author lyr
 * @create 2019/10/24 13:32
 */
public class FileDaoImpl extends BaseDao<Photo> implements FileDao<Photo> {
    private static final FileDaoImpl dao = new FileDaoImpl();

    public static FileDaoImpl getInstance() {
        return FileDaoImpl.dao;
    }

    private int cnt;

    private FileDaoImpl() {
        super(Photo.class);
    }

    private static final String GET_ONE_INFO = "select * from photo where photoId = ?";

    private int getCnt() {
        if (this.cnt > 0) {
            return this.cnt;
        }
        this.cnt = super.getCount(JdbcUtil.getConnection(), FileDao.GET_IMG_ID);
        return this.cnt;

    }

    //After reading the following code, scold me again
    {
        getCnt();
    }
    //别删，不然会有bug

    /**
     * @return 返回主键id
     */
    @Override
    public int getId() {
        return super.getCount(JdbcUtil.getConnection(), FileDao.GET_IMG_ID);

    }

    /**
     * 获取数据库中的一条记录
     *
     * @param id 数据库表的id字段
     * @return 返回要查询的javabean
     */
    @Override
    public Photo getBeanInfo(String id) {
        return super.selectOne(JdbcUtil.getConnection(), GET_ONE_INFO, id);
    }

    private static final String ADD_ONE_INFO = "insert into photo (photoId,photoPath1,photoPath2,photoPath3)VALUES( ?, ?, ?, ?)";

    /**
     * 添加一条数据进入数据库
     *
     * @param paths 存储的路径
     * @return 返回存储存储到数据库的主键 id
     */

    @Override
    public int addOne(LinkedList<String> paths) {
        if(paths.isEmpty())
        {
            return -1;
        }
        String[] pathList = new String[3];
        int i = 0;
        for (String t : paths) {
            pathList[i++] = t;
        }
        Lock lock = new ReentrantLock();
        lock.lock();
        //防止高并发写入时出问题，需要加锁
        i = this.cnt;
        try {
            super.addOne(JdbcUtil.getConnection(), ADD_ONE_INFO, i, pathList[0], pathList[1], pathList[2]);
            this.cnt++;
        } finally {
            lock.unlock();
        }
        //返回插入到数据库的主键值
        return i;

    }

}
