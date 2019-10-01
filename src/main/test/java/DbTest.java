import com.repairsys.bean.entity.Form;
import com.repairsys.dao.impl.form.FormDaoImpl;
import com.repairsys.dao.impl.form.FormListDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import org.junit.Test;

import java.sql.Date;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/27 10:13
 */
public class DbTest {
    @Test
    public void addForm()
    {
        FormDaoImpl.getInstance().apply("123d","宿舍水管坏d了，过来修，北苑17栋，B513",new Date(new java.util.Date().getTime()));
    }

    @Test
    public void addWorker()
    {
        WorkerDaoImpl.getInstance().register("123","1230","13654758962","123","123@qq.com");
    }


    @Test
    public void search()
    {
        List<Form> a =  FormDaoImpl.getInstance().queryByStudentId("1815");

    }
    @Test
    public void test()
    {
        List a = FormListDaoImpl.getInstance().getListById("181543430",0,"3","4");
        System.out.println(a);
    }

    @Test
    public void pageTest()
    {
        List a = FormListDaoImpl.getInstance().getPageList(1,4);


        System.out.println(a);
    }
}
