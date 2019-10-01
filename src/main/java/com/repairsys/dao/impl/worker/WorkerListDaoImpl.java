package com.repairsys.dao.impl.worker;

import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.AbstractPageDao;
import com.repairsys.util.easy.EasyTool;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/1 22:05
 */
public class WorkerListDaoImpl extends AbstractPageDao {

    private static final String GET_WORKER_LIST = "select * from worker limit ?,?";

    protected WorkerListDaoImpl(Class clazz) {
        super(clazz);
    }
    private static final WorkerListDaoImpl dao = new WorkerListDaoImpl(Worker.class);

    /**
     * 分页查询的功能
     *
     * @param targetPage 目标页面
     * @param size       记录条数
     * @return 一个bean集合
     */
    @Override
    public List getPageList(int targetPage, int size) {

       return super.getPageList(GET_WORKER_LIST,targetPage,size);
    }
}
