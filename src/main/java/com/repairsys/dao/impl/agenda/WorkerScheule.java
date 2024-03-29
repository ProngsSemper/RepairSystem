package com.repairsys.dao.impl.agenda;

import com.repairsys.bean.entity.RecommendedWorker;
import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.RecommendWorkerDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.util.db.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author lyr
 * @create 2019/10/7 20:46
 */
public class WorkerScheule extends TableDaoImpl implements Sortable {
    private static Logger logger = LoggerFactory.getLogger(WorkerScheule.class);

    private static final String UPDATE_BEGIN = "insert into wtime(`wkey`,`curTime`) VALUES(?,CURDATE());";
    private static final String DELETE_AFTER = "delete from wTime where `curTime` <> CURDATE()";
    private static final String GET_COUNT_OLD = "select count(*) from wTime where `curTime` <> CURDATE()";
    private static List<Worker> workerList = null;

    /**
     * 查询对于时间的工人，是否空闲
     */
    private static final String QUERY_DATE_WORKER = "select * from wTime where wType = '其他'||wType =?";

    private static final WorkerScheule WORKER_SCHEULE_DAO = new WorkerScheule();

    private WorkerScheule() {
    }

    private static final String SELECT_MIN_AND_MAX_DAY = "select min(`curtime`) from wtime union select max(`curtime`) from wtime";

    /**
     * 清理表中的垃圾并更新
     *
     * @return 返回是否成功
     */
    @Override
    public boolean cleanAndUpdateTable() {
        LinkedList<java.sql.Date> dayList = JdbcUtil.getDateList(SELECT_MIN_AND_MAX_DAY);
        if (dayList.isEmpty() || dayList.get(0) == null || dayList.get(1) == dayList.get(0)) {
            deleteTable();
            //清理并构建员工工作表单
            preCreateTable();
        } else {
            /**
             *
             * 把过期的表单迁移到旧表中去
             *
             * */
            FormListDaoImpl dao = FormListDaoImpl.getInstance();
            dao.moveTo();

            //这里就要判断 时间段了  [l,r] 和 m时间点的关系
            // m==l时，不更新， l<m<r 时
            java.sql.Date nowDay = new java.sql.Date(System.currentTimeMillis());
            if (nowDay.toString().equals(dayList.get(0).toString())) {
                return true;
            }

            int dt2 = nowDay.compareTo(dayList.get(1));
            //如果当前时间比数据库里记录的最大时间都要大，说明数据库里面的时间过期了，删除，重新写记录
            if (dt2 > 0) {
                deleteTable();
                preCreateTable();
                return true;
            } else {

                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(dayList.get(0));
                int day1 = calendar.get(Calendar.DAY_OF_YEAR);
                calendar.setTime(dayList.get(1));
                int day2 = calendar.get(Calendar.DAY_OF_YEAR);
                int d = day2 - day1;
                if (d < 7) {
                    //因为是记录 7天内的数据，如果数据小于7，说明是测试阶段，或者人为改动数据库，识别出来，并且创建7天的表
                    deleteTable();
                    preCreateTable();
                    return true;
                }
                //dt2小于0，说明在  [-无穷,m] 里面

                int dt1 = nowDay.compareTo(dayList.get(0));
                if (dt1 > 0) {

                    createTable2(7);
                    //由于之前清理表了，不可能小于0，而由于compareTo方法有比较毫秒，也不可能等于0
                    algoMethod2();
                    super.updateOne(JdbcUtil.getConnection(), DELETE_BEFORE);
                }
            }
        }
        return true;
    }

    private static final String DELETE_BEFORE = "delete from wtime where `curtime`<curdate()";

    public static WorkerScheule getInstance() {
        return WORKER_SCHEULE_DAO;
    }

    /**
     * @return 备份原来工人的数据
     * @deprecated 调用 updateAll即可，不需要使用这个单方面的更新
     */
    @Deprecated
    public boolean updateTable() {
        WorkerDaoImpl p = (WorkerDaoImpl) DaoFactory.getWorkerDao();
        QueryRunner queryRunner = p.getQueryRunner();
        List<Worker> arr = p.getAllWorkerList();

        int cnt = arr.size();
        Object[][] obj = new Object[cnt][];
        //工人的 key
        for (int i = 1; i <= cnt; ++i) {
            obj[i - 1] = new Object[1];
            obj[i - 1][0] = arr.get(i - 1).getwKey();
        }

        boolean b = true;
        try {
            queryRunner.batch(JdbcUtil.getConnection(), UPDATE_BEGIN, obj);
            // queryRunner.batch
        } catch (SQLException e) {
            b = false;
            logger.error("严重错误，更新日程表失败");
            e.printStackTrace();
        }

        return b;
    }

    /**
     * @return 删除垃圾数据
     * @deprecated 调用 updateAll即可，不需要使用这个单方面的更新
     */
    @Deprecated
    public boolean cleanTable() {
        WorkerDaoImpl p = (WorkerDaoImpl) DaoFactory.getWorkerDao();
        QueryRunner queryRunner = p.getQueryRunner();
        boolean b = true;
        try {
            queryRunner.update(JdbcUtil.getConnection(), DELETE_AFTER);
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
     * @deprecated 由于新的需求与原定的需求不一样，请不要调用此函数，此函数已经废弃
     */
    @Deprecated
    @Override
    public boolean updateAll() {

        int cnt = super.getCount(JdbcUtil.getConnection(), GET_COUNT_OLD);
        /*
         *
         * 这里是这样的，如果数据库表中发现了有不是当天时间的记录，那么就更新，返回true，更新成功，如果没发现不是当天的记录，说明更新过了，直接返回false
         *
         * */
        if (cnt == 0) {
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
    @Deprecated
    @Override
    public List<Worker> recommendByAppointment(int... hours) {
        throw new RuntimeException("该类方法已经过时");
    }

    /**
     * 根据预约的时间推荐空闲且合适的工人
     *
     * @param hour 预约的时间
     * @return 返回推荐的工人
     */
    @Override
    public List<Worker> recommendByAppointment(int hour, String wType) {
        boolean b = hour >= 9 && hour <= 11 || hour >= 14 && hour <= 18;
        if (!b) {
            return new LinkedList<>();
        }
        String tSql = "select wKey from wTime where t" + hour + " <1 and curTime = curdate()";

        String recommendSql = "select * from workers w where wType = null || wType ='" + wType + "' and w.wKey in ( " + tSql + " )";
        // System.out.println(recommendSql);
        List<Worker> list = WorkerDaoImpl.getInstance().getList(recommendSql);
        List<WTime> timeList = WorkerScheule.getInstance().getAllWorkerTimeList(tSql);

        list.sort(Comparator.comparingInt(Worker::getwKey));
        timeList.sort(Comparator.comparingInt(WTime::getwKey));
        Iterator<Worker> itL = list.iterator();
        Iterator<WTime> itR = timeList.iterator();

        while (itL.hasNext() && itR.hasNext()) {
            WTime timeTable = itR.next();
            Worker worker = itL.next();
            while (worker.getwKey() != timeTable.getwKey()) {
                timeTable = itR.next();
            }
            worker.setScore(timeTable.getSum());

        }
        list.sort(Comparator.comparingInt(Worker::getScore).reversed());

        return list;
    }

    /**
     * 推荐算法排序
     *
     * @param appointDate 学生预约的时间
     * @param hour        学生预约的时间，整点
     * @param wType       工人类型
     * @return 返回工人表单集合
     * @deprecated 进过后端同学讨论 ，认为不合理，后期会废弃（在 2019/10/20号以后会采用新的方法排序）
     */
    @Deprecated
    @Override
    public List<Worker> recommendByAppintment(Date appointDate, int hour, String wType) {

        boolean b = hour >= 9 && hour <= 11 || hour >= 14 && hour <= 18;
        if (!b) {
            return new LinkedList<>();
        }
        //切勿删除

        String tSql = "select wKey from wTime where curTime = '" + appointDate.toString() + "' ORDER BY t" + hour;
        logger.debug(tSql);
        //有修改和优化过sql语句，如果运行出错，可以检查一下这条命令

        String recommendSql = "select DISTINCT w.* from workers w left JOIN wtime wt on w.wKey = wt.wKey where wt.curTime = '" + appointDate.toString() + "' and w.wType ='" + wType + "' order by wt.t" + hour;
        logger.debug(recommendSql);
        List<Worker> list = WorkerDaoImpl.getInstance().getList(recommendSql);
        if (list == null || list.isEmpty() || list.get(0) == null) {
            logger.debug("处理异常");
            return new LinkedList<>();
        }
        List<WTime> timeList = WorkerScheule.getInstance().getAllWorkerTimeList(tSql);
        // System.out.println(timeList);
        logger.trace("{}", timeList);
        list.sort(Comparator.comparingInt(Worker::getwKey));
        timeList.sort(Comparator.comparingInt(WTime::getwKey));
        Iterator<Worker> itL = list.iterator();
        Iterator<WTime> itR = timeList.iterator();

        while (itL.hasNext() && itR.hasNext()) {
            WTime timeTable = itR.next();
            Worker worker = itL.next();
            while (worker.getwKey() != timeTable.getwKey()) {
                timeTable = itR.next();
            }
            worker.setScore(timeTable.getSum());
        }
        list.sort(Comparator.comparingInt(Worker::getScore).reversed());

        return list;

    }
    // private String UPDATE_TIME = "update wtime set t9 = t9+1 where curTime = ? and wkey = 1";

    /**
     * 更新工人的任务表
     *
     * @param date 设置日期
     * @param hour 设置几点
     * @param wKey 工人的id
     * @return
     */
    @Override
    public boolean setTime(String date, int hour, String wKey) {

        String thour = "t" + hour;
        String sql = "update wtime set " + thour + " = " + thour + "+1  where wKey " + " = " + wKey + " and curTime = '" + date + "'";
        // System.out.println(sql);
        logger.debug(sql);
        return super.addOne(JdbcUtil.getConnection(), sql);

    }

    /**
     * 新方法，推荐使用
     * 推荐算法排序，推荐调用的方法
     *
     * @param appointDate 学生预约的时间
     * @param hour        学生预约的时间，整点
     * @param wType       工人类型
     * @return 返回工人表单集合
     * @date 2019/10/19
     * @deprecated 还要根据工人的地点排序
     */
    public List<Worker> recommendByAppointmemntPlus(Date appointDate, int hour, String wType) {
        boolean b = hour >= 9 && hour <= 11 || hour >= 14 && hour <= 18;
        if (!b) {
            return new LinkedList<>();
        }

        String sql = "select wt.*,w.wType,w.wName,w.wMail,w.wTel from workers w" +
                " left JOIN wtime wt on w.wKey = wt.wKey where wt.curTime = '" + appointDate.toString() + "' " +
                "and w.wType = '" + wType + "' ORDER BY t" + hour;

        List<WTime> table = super.selectList(JdbcUtil.getConnection(), sql);
        LinkedList<Worker> carryZero = new LinkedList<>();
        LinkedList<Worker> carry = new LinkedList<>();
        for (WTime i : table) {
            if (i.getHourAt(hour) == 0) {
                carryZero.add(new Worker(i));
            } else {
                carry.add(new Worker(i));

            }
        }
        carryZero.sort(new Worker.CompareHandler());
        carry.sort(new Worker.CompareHandler());
        carryZero.addAll(carry);
        // System.out.println(carryZero);

        return carryZero;
    }

    private static final String RECOMMENDSQL_WORKER = "select DISTINCT w.wkey, w.wType,w.wName,w.wMail,w.wTel,FLAG,( t9+t10+t11+t14+t15+t16+t17+t18) as total,(select count(*) from form where appointDate=? and left(room,1) =? and wkey = w.wkey) locationCount  from workers w \n" +
            "left JOIN wtime wt on w.wKey = wt.wKey \n" +
            "left join form on form.wKey = w.wKey\n" +
            "\t\t\n" +
            "where wt.curTime = ? and w.wtype = ?";

    /**
     * <code>
     * <p>
     * 优先级：时间>工作量>位置
     * <p>
     * 工人在北苑的任务较多，管理员处理报修单页面 这个报修单是北苑的 则把在北苑工作量多的工人排在前面
     * <p>
     * 报修单：17号9点 北苑
     * <p>
     * 甲：17号9点有空 总任务5个 在北苑的任务有4个
     * 乙：17号9点没空 总任务4个 在北苑的任务4个
     * 丙：17号9点有空 总任务4个 在北苑的任务有3个
     * <p>
     * 丙>甲>乙
     * </code>
     *
     * @param hour
     * @param location 根据工人的地点排序
     * @return
     */

    public List<RecommendedWorker> recommendByAppointmemntPlusPlus(Date appointDate, int hour, String wType, String location) {
        boolean b = hour >= 9 && hour <= 11 || hour >= 14 && hour <= 18;
        if (!b) {
            return new LinkedList<>();
        }
        String sql = RECOMMENDSQL_WORKER.replace("FLAG", "t" + hour);

        List<RecommendedWorker> table = RecommendWorkerDaoImpl.getInstance().getList(sql,
                appointDate, location, appointDate, wType
        );
        Stream<RecommendedWorker> first = table.stream().filter(i -> i.getTime(hour) == 0).sorted(
                Comparator.comparing(RecommendedWorker::getLocationCount).reversed()
                        .thenComparing(RecommendedWorker::getTotal)
        );

        Stream<RecommendedWorker> second = table.stream().filter(i -> i.getTime(hour) != 0).sorted(
                Comparator.comparing(RecommendedWorker::getLocationCount, Comparator.comparingInt(i -> i)).reversed()
                        .thenComparing(RecommendedWorker::getTotal, Comparator.comparingInt(i -> i))
                //RecommendedWorker::getTotal, Comparator.comparingInt(i -> i)
        );

        return Stream.concat(first, second).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * curday 从0 加到7，创建一个星期的记录
     */
    private static final String UPDATE_OLD_PERSON_SERVEN_DAY = "insert into wtime(`wkey`,`curTime`) VALUES(?,DATE_FORMAT(DATE_ADD(NOW(),INTERVAL ? DAY),'%Y-%m-%d'))";

    /**
     * @param day
     * @return
     * @deprecated 请不要直接用
     */
    public boolean createTable2(Integer day) {
        WorkerDaoImpl p = (WorkerDaoImpl) DaoFactory.getWorkerDao();
        QueryRunner queryRunner = p.getQueryRunner();
        List<Worker> arr = p.getAllWorkerList();

        if (WorkerScheule.workerList == null) {
            WorkerScheule.workerList = arr;
        } else {
            LinkedList<Integer> diffSet = new LinkedList<>();
            arr.stream().forEach(
                    (Worker w) ->
                    {
                        if (!WorkerScheule.workerList.contains(w)) {
                            diffSet.add(w.getwKey());
                        }
                    }
            );
            if (!diffSet.isEmpty()) {
                logger.error("正在更新工人表单");
                // System.out.println("--dd--");
                for (int i : diffSet) {
                    for (int j = 0; j <= 6; ++j) {
                        updateAndInsert(i, j);
                    }
                }
                WorkerScheule.workerList = arr;
            }

        }

        int cnt = arr.size();
        Object[][] obj = new Object[cnt][];
        //工人的 key
        for (int i = 1; i <= cnt; ++i) {
            obj[i - 1] = new Object[2];
            obj[i - 1][0] = arr.get(i - 1).getwKey();
            obj[i - 1][1] = day;
        }
        boolean b = true;
        try {
            queryRunner.batch(JdbcUtil.getConnection(), UPDATE_OLD_PERSON_SERVEN_DAY, obj);

        } catch (SQLException e) {
            b = false;
            logger.error("严重错误，更新日程表失败");
            e.printStackTrace();
        }

        return b;
    }

    /**
     * 创建一个星期的工人表
     */
    public void preCreateTable() {
        for (int i = 0; i <= 7; ++i) {
            createTable2(i);
        }

    }

    private static final String INSERT_NEW_WORKER_TABLE = "insert into wtime(`wkey`,`curTime`) VALUES(1,DATE_FORMAT(DATE_ADD(NOW(),INTERVAL 1 DAY),'%Y-%m-%d'))";

    private void updateAndInsert(int key, int day) {
        super.updateOne(JdbcUtil.getConnection(), INSERT_NEW_WORKER_TABLE, key, day);
    }

    public void updateWtime(String day, String hour, int key) {
        String sql = "update wtime set t" + hour + " = t" + hour + "-1 where wkey = ? AND curTime = '" + day + "'";
        super.updateOne(JdbcUtil.getConnection(), sql, key);

    }

}
