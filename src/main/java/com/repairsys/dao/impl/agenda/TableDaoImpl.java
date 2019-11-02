package com.repairsys.dao.impl.agenda;

import com.repairsys.bean.entity.WTime;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.TableListDao;
import com.repairsys.util.db.JdbcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/8 13:00
 */
public abstract class TableDaoImpl extends BaseDao<WTime> implements TableListDao {
    private static final Logger logger = LoggerFactory.getLogger(TableDaoImpl.class);

    private static final String[] ASSIGN_NAME = {
            " t9 = ? "
            ,
            " t10 = ? "
            , " t11 = ? "
            , " t14 =? "
            , " t15=? "
            , " t16=? "
            , " t17=? "
            , " t18=? "

    };

    public TableDaoImpl() {
        super(WTime.class);
    }

    private static final String GET_ALL_WTIME = "select * from wtime where `curTime` <> CURDATE()";
    private static final String RAW_SET = "update wtime set ";
    private static final String GET_ALL_INFO = "select * from wtime";

    public List<WTime> getAllWorkerTimeList() {
        return super.selectList(JdbcUtil.getConnection(), GET_ALL_INFO);
    }

    public List<WTime> getAllWorkerTimeList(String sql) {
        return super.selectList(JdbcUtil.getConnection(), sql);
    }

    /**
     * 获得工人的表单集合
     *
     * @return 获得工人表单集合
     * @deprecated 使用子类的方法
     */
    @Deprecated
    @Override
    public List<WTime> getTableList() {
        return super.selectList(JdbcUtil.getConnection(), GET_ALL_WTIME);
    }

    /**
     * 性能低，最好一口气写完
     *
     * @deprecated 分装了其他方法，直接调用即可
     */
    @Deprecated
    public int[] getSumList() {
        List<WTime> arr = super.selectList(JdbcUtil.getConnection(), GET_ALL_WTIME);
        int[] res = new int[arr.size()];
        for (int i = 0; i < res.length; ++i) {
            res[i] = arr.get(i).getSum();
        }
        return res;

    }

    /**
     * 尝试实现一个自动更新数据库表单的算法
     *
     * @return 不推荐用此方法，因为封装了其他方法，直接调用即可
     * @deprecated 最初的数据库建模与新要求有冲突，已经重新写了份新方法，不要再调用此方法
     */
    @Deprecated
    public boolean algoMethod() {
        List<WTime> arr = super.selectList(JdbcUtil.getConnection(), GET_ALL_WTIME);
        int[] res = new int[arr.size()];
        for (int i = 0; i < res.length; ++i) {
            res[i] = arr.get(i).getSum();
        }

        for (int i = 0; i < res.length; ++i) {
            if (res[i] <= 0) {
                continue;
            }
            LinkedList tmp = new LinkedList();

            StringBuilder finalSql = new StringBuilder(RAW_SET + ASSIGN_NAME[0]);
            int r = 0;
            for (int j = 0; j < 8; ++j) {
                int t = arr.get(i).getTimeAt(j);
                if (t > 0) {
                    if (r == 0) {
                        tmp.add(t);
                        ++r;
                    } else {
                        finalSql.append(", ").append(ASSIGN_NAME[r++]);
                        tmp.add(t);
                    }
                }
            }
            finalSql.append("where wKey = ").append(arr.get(i).getwKey()).append(" and `curTime`= CURDATE()");
            System.out.println(finalSql.toString());
            super.updateOne(JdbcUtil.getConnection(), finalSql.toString(), tmp.toArray());
            logger.info(" {} ", tmp);

        }

        return true;
    }

    private static final String GET_YESTERDAY_RECORD_WORKERS = "select * FROM wtime where curTime = CURDATE()-1";
    private static final String SELECT_CURDAY_RECORD = "select * FROM wtime where curTime = CURDATE()";

    /**
     * 对工人表里的信息进行更新，把昨天工人未完成的任务分配到今天早上完成
     * 注意：这里只是其中的一小部分环节，请不要直接调用
     */
    public void algoMethod2() {

        //把昨天的数据库记录查出来
        List<WTime> arr = super.selectList(JdbcUtil.getConnection(), GET_YESTERDAY_RECORD_WORKERS);
        //把今天的数据库记录查出来
        List<WTime> curDayTable = super.selectList(JdbcUtil.getConnection(), SELECT_CURDAY_RECORD);
        /*
         *
         * 两条记录合并在一起
         *
         * */

        int[] res = new int[arr.size()];
        //res 数组是存储工人昨天任务表的总数，如果未完成3件事情，就要丢到今天早上来完成
        for (int i = 0; i < res.length; ++i) {
            res[i] = arr.get(i).getSum();
        }

        for (int i = 0; i < res.length; ++i) {
            if (res[i] <= 0) {
                //如果工人昨天的任务已经完成，那么他的任务清单一定为0，跳过去
                continue;
            }

            //第 i号 工人 的昨天任务总数
            LinkedList tmp = new LinkedList();
            //参数的记录，昨天的任务和今天的任务做加法，得到的任务数
            StringBuilder finalSql = new StringBuilder(RAW_SET + ASSIGN_NAME[0]);
            int r = 0;
            WTime todayTaskList = curDayTable.get(i);
            for (int j = 0; j < 8; ++j) {
                int t = arr.get(i).getTimeAt(j);
                if (t > 0) {
                    if (r == 0) {
                        tmp.add(t + todayTaskList.getTimeAt(r++));

                    } else {
                        finalSql.append(", ").append(ASSIGN_NAME[r]);
                        tmp.add(t + todayTaskList.getTimeAt(r++));
                    }
                }
            }
            finalSql.append(" where wKey = ").append(arr.get(i).getwKey()).append(" and `curTime`= CURDATE() ");
            System.out.println(finalSql.toString());
            super.updateOne(JdbcUtil.getConnection(), finalSql.toString(), tmp.toArray());
            logger.info(" {} ", tmp);

        }
    }


    /*
     * 上面是对旧记录的迁移操作
     *以下是 对于检查 wtime表中的一些函数，分别用来删除错误记录，然后建立新记录
     *
     *
     * */

    /**
     * 如果检查到数据库的表已经没有更新了，要重新更新一遍
     */
    protected boolean deleteTable() {
        return super.updateOne(JdbcUtil.getConnection(), "delete from wtime");
    }

    /**
     * 清理表中的垃圾并更新
     *
     * @return 返回是否成功
     */
    public abstract boolean cleanAndUpdateTable();

}
