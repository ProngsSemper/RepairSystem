import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.dao.TableListDao;
import com.repairsys.dao.impl.agenda.TableDaoImpl;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.util.db.JdbcUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/8 12:20
 */
public class UpDateProcedureTest {

    @Test
    public void loggerPrint()
    {
        WorkerScheule dao = WorkerScheule.getInstance();
        boolean b = dao.updateTable();
        System.out.println(b);
    }



    @Test
    public void loggerCleanBefore()
    {
        WorkerScheule dao = WorkerScheule.getInstance();
        boolean b = dao.cleanTable();
        System.out.println(b);
    }


    @Test
    public void printTableList()
    {
        TableDaoImpl dao = WorkerScheule.getInstance();
        List p = dao.getTableList();
        System.out.println(p);
        WTime table = (WTime) p.get(0);
        System.out.println(table.getSum());
    }

    @Test
    public void algoMethod()
    {
        WorkerScheule p = WorkerScheule.getInstance();
        boolean b= p.updateAll();
        System.out.println(b);
    }

    @Test
    public void updateAllInformationOfWorkerTimeTable()
    {
        WorkerScheule p = WorkerScheule.getInstance();
        p.updateAll();
    }

    @Test
    public void print11()
    {
        List list = WorkerScheule.getInstance().recommendByAppointment(10,"木");
        System.out.println(list);
    }
    /**
     *
     * @date 2019/10/15
     * */

    @Test
    public void createWorkerTimeTable()
    {
        WorkerScheule p = WorkerScheule.getInstance();
        for(int i=-1;i<=6;++i)
        {
            p.createTable2(i);
        }
    }

    @Test
    public void testAlgoMethod()
    {
        WorkerScheule p = WorkerScheule.getInstance();
        p.algoMethod2();
    }



    @Test
    public void testMoveTableInformation() throws SQLException {
        List<java.sql.Date> res = JdbcUtil.getDateList("select min(`day`) FROM tes union select max(`day`) from tes");
        System.out.println(res.isEmpty()||res.get(0)==null);
    }



















}
