import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import com.repairsys.dao.impl.board.BoardDaoImpl;
import com.repairsys.dao.impl.developer.DeveloperDao;
import com.repairsys.dao.impl.file.FileDaoImpl;
import com.repairsys.dao.impl.form.FormDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.dao.impl.worker.WorkerListDaoImpl;
import com.repairsys.service.FormService;
import com.repairsys.service.impl.form.FormServiceImpl;
import com.repairsys.service.impl.table.ExcelServiceImpl;
import com.repairsys.util.db.JdbcUtil;
import com.repairsys.util.time.TimeUtil;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.Test;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/27 10:13
 */
public class DbTest {

    @Test
    public void printf()
    {
        String str = "[\n" +
                " {\"stuMail\":\"798237844@qq.com\",\"queryCode\":2,\"formId\":146,\"day\":\"06\",\"hour\":\"16\"},\n" +
                " {\"stuMail\":\"915147193@qq.com\",\"queryCode\":2,\"formId\":149,\"day\":\"05\",\"hour\":\"09\"}\n" +
                "]";
        System.out.println(str);
        List<Form> list = JSONObject.parseArray(str,Form.class);
        System.out.println(list);
        for(Form i:list)
        {
            //queryRunner 批处理
        }

    }

    @Test
    public void printPath()
    {
        String p = "http://localhost:80/";
        String p2 = "F:\\算法\\我的团队项目\\p1\\target\\RepairSystem\\upload\\img\\\\c9c7951d-8e9a-4e71-9ac4-507128d487df无标题.png";
        String y = p+p2.substring(p2.indexOf("upload"),p2.length());
        System.out.println(y);

    }


    @Test
    public void  getpath()
    {
        Object t =  FileDaoImpl.getInstance().getImgPath("81");
        System.out.println(t);
    }


    @Test
    public void getQuery() throws SQLException {




        QueryRunner r = new QueryRunner();
        int row = r.update(JdbcUtil.getConnection(),
                "insert into tes(`name`,`password`,`day`) values('dddtd','dd',CURDATE());");

        System.out.printf("行数:"+row);

    }

    @Test
    public void updateI()
    {
        WorkerScheule.getInstance().setTime(TimeUtil.getCurTime(),9,"1");
        System.out.println(TimeUtil.getTime(1,2));
    }




    @Test
    public void addForm() {
        FormDaoImpl.getInstance().apply("123d", "宿舍水管坏d了，过来修，北苑17栋，B513", new Date(new java.util.Date().getTime()));
    }

    @Test
    public void addBoard() {
        AdminDaoImpl.getInstance().releaseBoard("test", new Timestamp(new java.util.Date().getTime()));
    }

    @Test
    public void addWorker() {
        WorkerDaoImpl.getInstance().register("123", "1230", "13654758962", "123", "123@qq.com");
    }

    @Test
    public void search() {
        List<Form> a = FormDaoImpl.getInstance().queryByStudentId("1815");

    }

    @Test
    public void test() {
        List a = FormListDaoImpl.getInstance().getListById("181543430", 0, "3", "4");
        System.out.println(a);
    }

    @Test
    public void pageTest() {
        List a = FormListDaoImpl.getInstance().getPageList(1, 4);

        System.out.println(a);
    }

    @Test
    public void workerPage() {
        List<Worker> a = WorkerListDaoImpl.getInstance().getPageList(0, 4);
        System.out.println(a);

    }

    @Test
    public void divideTest() {
        FormService formService = new FormServiceImpl();
        int count = formService.getTotalCount();
        int size = 1;
        int totalPage = 1;

        for (int page = 1; page <= totalPage; ++page) {
            Result p = formService.getPageList(page, size);
            System.out.println(p.getData());
        }

    }

    @Test
    public void developer() {
        boolean b = DeveloperDao.getInstance().register("181549422", "huxi9138");
        System.out.println(b);
    }

    @Test
    public void loginDeveloper() {
        // Developer developer = DeveloperDao.getInstance().login("lyr", "");
        // System.out.println(developer);

        DeveloperDao.getInstance().register("181549422","huxi9138" );
        System.out.println();

    }

    @Test
    public void searchWorkers() {
        List<Worker> list = WorkerDaoImpl.getInstance().fuzzySearchWorkers("刘");
        System.out.println(list);
    }

    @Test
    public void historyBoard() {
        List list = BoardDaoImpl.getInstance().getHistoryBoard();
        System.out.println(list);
    }

    @Test
    public void workerForm(){
        Worker worker = WorkerDaoImpl.getInstance().getWorkerKeyById("4566");
        List list = FormListDaoImpl.getInstance().queryAllFormIdByWorkerKey(worker.getwKey(), 1, 5);
        System.out.println(list);
    }



    //-------------------------------



    @Test
    public void recommend()
    {
        WorkerScheule.getInstance().recommendByAppointmemntPlus(new Date(System.currentTimeMillis()),9,"木工");
    }


    @Test
    public void test33()
    {
        // List<ExcelTable> list = WorkerTableImpl.getInstance().getTable();
        // System.out.println(list);
        ExcelServiceImpl.getInstance().exportOneByOne(new Result());
    }

    @Test
    public void printTime()
    {
        WorkerScheule.getInstance().setTime(TimeUtil.getCurTime(),9,"1");

    }


    @Test
    public void printTest()
    {
    //    upload
        FileDaoImpl dao = FileDaoImpl.getInstance();
        LinkedList<String> list = new LinkedList();
        list.add("123");
        list.add("123");
        list.add("123");
        dao.addOne(list);

    }

    @Test
    public void print()
    {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime().getDay());
    }








}
