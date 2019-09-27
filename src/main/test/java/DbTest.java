import com.repairsys.bean.entity.Student;
import com.repairsys.dao.impl.FormDaoImpl;
import com.repairsys.dao.impl.StudentDaoImpl;
import com.repairsys.dao.impl.WorkerDaoImpl;
import org.junit.Test;

import java.sql.Date;

/**
 * @Author lyr
 * @create 2019/9/27 10:13
 */
public class DbTest {
    @Test
    public void addForm()
    {
        FormDaoImpl.getInstance().apply("123","宿舍水管坏了，过来修，北苑17栋，B513",new Date(new java.util.Date().getTime()));
    }

    @Test
    public void addWorker()
    {
        WorkerDaoImpl.getInstance().register("123","1230","13654758962","123","123@qq.com");
    }
    @Test
    public void loginStudent()
    {
        // StudentDaoImpl.getInstance().register("123","123","12","1","12");
        Object b = StudentDaoImpl.getInstance().login("123","123");
        System.out.println(b==null);
        boolean t =false;
        t= StudentDaoImpl.getInstance().exists("123");
        System.out.println(t);
    }
}
