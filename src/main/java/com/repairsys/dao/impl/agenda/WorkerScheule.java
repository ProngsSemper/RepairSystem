package com.repairsys.dao.impl.agenda;

import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.BaseDao;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.dao.impl.worker.WorkerListDaoImpl;
import com.repairsys.util.db.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.*;

/**
 * @Author lyr
 * @create 2019/10/7 20:46
 */
public class WorkerScheule extends TableDaoImpl implements Sortable {
    private static Logger logger = LoggerFactory.getLogger(WorkerScheule.class);

    private static final String UPDATE_BEGIN= "insert into wtime(`wkey`,`curTime`) VALUES(?,CURDATE());";
    private static final String DELETE_AFTER = "delete from wTime where `curTime` <> CURDATE()";
    private static final String GET_COUNT_OLD = "select count(*) from wTime where `curTime` <> CURDATE()";
    private static final WorkerScheule WORKER_SCHEULE_DAO = new WorkerScheule();

    private WorkerScheule() {
    }

    public static WorkerScheule getInstance()
    {
        return WORKER_SCHEULE_DAO;
    }


    /**
     * @deprecated 调用 updateAll即可，不需要使用这个单方面的更新
     * @return 备份原来工人的数据
     */
    public boolean updateTable() {
        WorkerDaoImpl p = (WorkerDaoImpl)DaoFactory.getWorkerDao();
        QueryRunner queryRunner = p.getQueryRunner();
        List<Worker> arr = p.getAllWorkerList();

        int cnt = arr.size();
        Object[][] obj = new Object[cnt][];
        for(int i=1;i<=cnt;++i)
        {
            obj[i-1] = new Object[1];
            obj[i-1][0] = arr.get(i-1).getwKey();
        }


        boolean b = true;
        try {
            queryRunner.batch(JdbcUtil.getConnection(),UPDATE_BEGIN,obj);
            // queryRunner.batch
        } catch (SQLException e) {
            b = false;
            logger.error("严重错误，更新日程表失败");
            e.printStackTrace();
        }

        return b;
    }


    /**
     * @deprecated 调用 updateAll即可，不需要使用这个单方面的更新
     * @return 删除垃圾数据
     */
    public boolean cleanTable()
    {
        WorkerDaoImpl p = (WorkerDaoImpl)DaoFactory.getWorkerDao();
        QueryRunner queryRunner = p.getQueryRunner();
        boolean b = true;
        try {
            queryRunner.update(JdbcUtil.getConnection(),DELETE_AFTER);
        } catch (SQLException e) {
            b = false;
            logger.error("严重错误，删除无用信息失败");
            e.printStackTrace();
        }
        return b;
    }


    /**
     * 管理员调用此方法，即可更新数据库表，不需要用别的什么方法了
     *
     * @return 返回更新的结果是否成功
     */
    @Override
    public boolean updateAll() {

        int cnt = super.getCount(JdbcUtil.getConnection(),GET_COUNT_OLD);
        /*
        *
        * 这里是这样的，如果数据库表中发现了有不是当天时间的记录，那么就更新，返回true，更新成功，如果没发现不是当天的记录，说明更新过了，直接返回false
        *
        * */
        if(cnt==0)
        {
            return false;
        }
        this.updateTable();
        super.algoMethod();

        this.cleanTable();

        return true;
    }




    /**
     * 根据预约的时间推荐空闲且合适的工人
     *
     * @param hours 预约的时间
     * @return 返回推荐的工人
     */
    @Override
    public List<Worker> recommendByAppointment(int... hours) {
        return null;
    }

    /**
     * 根据预约的时间推荐空闲且合适的工人
     *
     * @param hour 预约的时间
     * @return 返回推荐的工人
     */
    @Override
    public List<Worker> recommendByAppointment(int hour,String  wType) {
        boolean b = hour>=9&&hour<=11||hour>=14&&hour<=18;
        if(!b)
        {
            return new LinkedList<>();
        }
        String tSql = "select wKey from wTime where t"+hour+" <1 and curTime = curdate()";

        String recommendSql = "select * from workers w where wType = null || wType ='"+wType +"' and w.wKey in ( "+tSql+" )";
        System.out.println(recommendSql);
        List<Worker> list = WorkerDaoImpl.getInstance().getList(recommendSql);
        List<WTime> timeList = WorkerScheule.getInstance().getAllWorkerTimeList(tSql);

        list.sort(Comparator.comparingInt(Worker::getwKey));
        timeList.sort(Comparator.comparingInt(WTime::getwKey));
        Iterator<Worker> itL = list.iterator();
        Iterator<WTime> itR = timeList.iterator();

        while (itL.hasNext()&&itR.hasNext())
        {
            WTime timeTable = itR.next();
            Worker worker = itL.next();
            while (worker.getwKey()!=timeTable.getwKey())
            {
                timeTable = itR.next();
            }
            worker.setScore(timeTable.getSum());


        }
        list.sort(Comparator.comparingInt(Worker::getScore).reversed());

        return list;











    }
}
