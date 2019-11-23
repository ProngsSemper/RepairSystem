import com.repairsys.bean.entity.WTime;
import com.repairsys.dao.impl.agenda.TableDaoImpl;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.util.db.JdbcUtil;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/10/8 12:20
 */
public class UpDateProcedureTest {

    @Test
    public void loggerPrint() {
        WorkerScheule dao = WorkerScheule.getInstance();
        boolean b = dao.updateTable();
        System.out.println(b);
    }

    @Test
    public void loggerCleanBefore() {
        WorkerScheule dao = WorkerScheule.getInstance();
        boolean b = dao.cleanTable();
        System.out.println(b);
    }

    @Test
    public void printTableList() {
        TableDaoImpl dao = WorkerScheule.getInstance();
        List p = dao.getTableList();
        System.out.println(p);
        WTime table = (WTime) p.get(0);
        System.out.println(table.getSum());
    }

    @Test
    public void algoMethod() {
        WorkerScheule p = WorkerScheule.getInstance();
        boolean b = p.updateAll();
        System.out.println(b);
    }

    @Test
    public void updateAllInformationOfWorkerTimeTable() {
        WorkerScheule p = WorkerScheule.getInstance();
        p.updateAll();
    }

    @Test
    public void print11() {
        List list = WorkerScheule.getInstance().recommendByAppointment(10, "木");
        System.out.println(list);
    }

    /**
     * @date 2019/10/15
     */

    @Test
    public void createWorkerTimeTable() {
        WorkerScheule p = WorkerScheule.getInstance();
        for (int i = -1; i <= 6; ++i) {
            p.createTable2(i);
        }
    }

    @Test
    public void testAlgoMethod() {
        WorkerScheule p = WorkerScheule.getInstance();
        p.algoMethod2();
    }

    @Test
    public void testMoveTableInformation() throws SQLException {
        // List<java.sql.Date> res = JdbcUtil.getDateList("select min(`day`) FROM tes union select max(`day`) from tes");
        // System.out.println(res.isEmpty()||res.get(0)==null);
        WorkerScheule.getInstance().preCreateTable();
    }

    @Test
    public void print() {
        Date d = new java.sql.Date(new java.util.Date().getTime());
        LinkedList<java.sql.Date> dayList = JdbcUtil.getDateList("select min(`curtime`) from wtime union select max(`curtime`) from wtime");
        //会有空指针异常
        System.out.println(d.compareTo(dayList.get(0)));
        System.out.println(d.compareTo(dayList.get(1)));
        System.out.println(d.toString().equals(dayList.get(0).toString()));

        System.out.println(dayList.get(0));
        System.out.println(dayList.get(1));
        System.out.println(d);

    }

    @Test
    public void print2() {
        Date d = new java.sql.Date(System.currentTimeMillis() - 410000000);
        Date N = new java.sql.Date(System.currentTimeMillis());
        System.out.println(d);
        System.out.println(N);
        String t = d.toString();
        String r = N.toString();
        String ll, rr;
        System.out.println(ll = t.substring(t.length() - 2, t.length()));
        System.out.println(rr = r.substring(t.length() - 2, t.length()));
        int dd = Integer.parseInt(ll) - Integer.parseInt(rr);
        System.out.println(dd);

    }

    @Test
    public void dayPrint() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date N = new java.sql.Date(System.currentTimeMillis());
        calendar.setTime(N);
        int day1 = calendar.get(Calendar.DAY_OF_YEAR);
        Date d = new java.sql.Date(System.currentTimeMillis() - 410000000);
        calendar.setTime(d);
        int day2 = calendar.get(Calendar.DAY_OF_YEAR);
        System.out.println(day1 - day2);
        System.out.println(N + "  ---   " + d);

    }

    @Test
    public void generateCaseOfDb() {
        for (int i = -1; i < 3; ++i) {
            WorkerScheule.getInstance().createTable2(i);
        }
    }

    @Test
    public void databaseUpdateAuto() {
        WorkerScheule.getInstance().algoMethod2();
    }

    @Test
    public void dataBaseUpdate() {
        WorkerScheule.getInstance().cleanAndUpdateTable();
    }

    @Test
    public void playThread() {
        Thread p = new Thread(() -> {

            while (true) {
                System.out.println(1123);
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(1);
            }
        });
        p.setDaemon(true);
        p.start();

    }

}
