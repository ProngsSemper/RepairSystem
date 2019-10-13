package com.repairsys.dao.impl.worker;

import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.AbstractPageDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/1 22:05
 */
public class WorkerListDaoImpl extends AbstractPageDao {
    public enum Column {
        /**
         * 用来枚举数据库的列名字
         */
        wKey("wKey"),
        wId("wId"),
        wName("wName"),
        wTel("wTel"),
        wPassword("wPassword"),
        wMail("wMail"),

        ;
        String name;

        /**
         * 数据库查询时候需要用到得到列
         *
         * @param name 数据库的字段名字
         */
        Column(String name) {
            this.name = name;
        }

    }

    private static final String GET_WORKER_LIST = "select * from workers limit ?,?";
    private static final String BASE_QUERY = "select * from workers ";


    protected WorkerListDaoImpl(Class clazz) {
        super(clazz);
    }

    private static final WorkerListDaoImpl DAO = new WorkerListDaoImpl(Worker.class);

    public static WorkerListDaoImpl getInstance() {
        return DAO;
    }

    /**
     * 分页查询的功能
     *
     * @param targetPage 目标页面
     * @param size       记录条数
     * @return 一个bean集合
     */
    @Override
    public List getPageList(int targetPage, int size) {

        return super.getPageList(GET_WORKER_LIST, targetPage, size);
    }

    /**
     * 查询对应的工人
     *
     * @param columns 数据库 worker表的列名字
     * @param args    数据库的参数
     * @return 返回worker的bean集合
     * @deprecated <pre>{@code  public void printWorkerList()
     *     {
     *         WorkerListDaoImpl.Column[] arr = {
     *             WorkerListDaoImpl.Column.wId,
     *             WorkerListDaoImpl.Column.wKey
     *
     *         };
     *         List a = WorkerListDaoImpl.getInstance().getWorkerByCondition(arr,new String[]{"123","0"});
     *         System.out.println(a);
     *     }}
     * </pre>
     */
    @Deprecated
    public List<Worker> getWorkerByCondition(WorkerListDaoImpl.Column[] columns, String[] args) {
        if (columns == null || args == null) {
            return null;
        }
        if ((columns.length != args.length) || (columns.length < 1)) {
            return null;
        }

        StringBuilder sb = new StringBuilder(BASE_QUERY + " where 1=1 ");
        for (int i = 0; i < columns.length; ++i) {
            sb.append(" and " + columns[i].name + " = " + args[i]);
        }

        return super.selectList(JdbcUtil.getConnection(), sb.toString());

    }

}
