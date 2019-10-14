package com.repairsys.dao.impl.agenda;

import com.repairsys.bean.entity.WTime;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.TableListDao;
import com.repairsys.util.db.JdbcUtil;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/8 13:00
 */
public abstract class TableDaoImpl extends BaseDao<WTime> implements TableListDao {

    private static final String[] ASSIGN_NAME = {
            " t9 = ? "
            ,
            " t10 = ? "
            ," t11 = ? "
            ," t14 =? "
            ," t15=? "
            ," t16=? "
            ," t17=? "
            ," t18=? "

    };

    public TableDaoImpl() {
        super(WTime.class);
    }
    private static final String GET_ALL_WTIME = "select * from wtime where `curTime` <> CURDATE()";
    private static final String RAW_SET = "update wtime set ";
    private static final String GET_ALL_INFO = "select * from wtime";

    public List<WTime> getAllWorkerTimeList()
    {
        return super.selectList(JdbcUtil.getConnection(),GET_ALL_INFO);
    }
    public List<WTime> getAllWorkerTimeList(String sql)
    {
        return super.selectList(JdbcUtil.getConnection(),sql);
    }


    /**
     * 获得工人的表单集合
     *
     * @return 获得工人表单集合
     * @deprecated
     */
    @Override
    public List<WTime> getTableList() {
        return super.selectList(JdbcUtil.getConnection(),GET_ALL_WTIME);
    }



    /**
     *
     * 性能低，最好一口气写完
     * @deprecated 分装了其他方法，直接调用即可
     * */
    @Deprecated
    public int[] getSumList()
    {
        List<WTime> arr = super.selectList(JdbcUtil.getConnection(),GET_ALL_WTIME);
        int[] res = new int[arr.size()];
        for(int i=0;i<res.length;++i)
        {
            res[i] = arr.get(i).getSum();
        }
        return res;

    }



    /**
     * 尝试实现一个自动更新数据库表单的算法
     * @return
     * 不推荐用此方法，因为封装了其他方法，直接调用即可
     */
    public boolean algoMethod()
    {
        List<WTime> arr = super.selectList(JdbcUtil.getConnection(),GET_ALL_WTIME);
        int[] res = new int[arr.size()];
        for(int i=0;i<res.length;++i)
        {
            res[i] = arr.get(i).getSum();
        }

        for(int i=0;i<res.length;++i)
        {
            if(res[i]<=0) {
                continue;
            }
            LinkedList tmp = new LinkedList();
            tmp.add(1);
            StringBuilder finalSql = new StringBuilder(RAW_SET+ASSIGN_NAME[0]);
            for(int j=1;j<res[i];++j)
            {
                finalSql.append(", "+ASSIGN_NAME[j]);
                tmp.add(1);
            }
            finalSql.append("where wKey = "+(i+1)+" and `curTime`= CURDATE()");
            System.out.println(finalSql.toString());
            super.updateOne(JdbcUtil.getConnection(),finalSql.toString(),tmp.toArray());

        }








        return true;
    }







}
