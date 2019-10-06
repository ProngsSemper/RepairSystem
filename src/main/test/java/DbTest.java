import com.repairsys.bean.entity.Developer;
import com.repairsys.bean.entity.Form;
import com.repairsys.bean.entity.Worker;
import com.repairsys.bean.vo.Result;
import com.repairsys.dao.impl.developer.DeveloperDao;
import com.repairsys.dao.impl.form.FormDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.dao.impl.worker.WorkerListDaoImpl;
import com.repairsys.service.FormService;
import com.repairsys.service.impl.form.FormServiceImpl;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/27 10:13
 */
public class DbTest {
    @Test
    public void addForm() {
        FormDaoImpl.getInstance().apply("123d", "宿舍水管坏d了，过来修，北苑17栋，B513", new Date(new java.util.Date().getTime()));
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
        boolean b = DeveloperDao.getInstance().register("lyr", "422525");
        System.out.println(b);
    }

    @Test
    public void loginDeveloper() {
        Developer developer = DeveloperDao.getInstance().login("lyr", "422525000");
        System.out.println(developer);

        developer = DeveloperDao.getInstance().login("lyr", "422525");
        System.out.println(developer);

    }

    @Test
    public void searchWorkers() {
        List<Worker> list = WorkerDaoImpl.getInstance().fuzzySearchWorkers("刘");
        System.out.println(list);
    }

}
