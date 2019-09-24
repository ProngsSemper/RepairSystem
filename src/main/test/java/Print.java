import com.repairsys.bean.entity.Admin;
import com.repairsys.dao.AdminDao;
import com.repairsys.dao.impl.AdminDaoImpl;
import com.repairsys.util.string.StringUtils;
import org.junit.Test;

import java.util.List;

/**
 * @author Prongs
 * @date 2019/9/22
 * 这个是junit包，试错
 */

public class Print {
    @Test
    public void printSome() {
        System.out.println("hello");

    }

    @Test
    public void say()
    {
        String sql = "select * from administrators";
        List<Admin> list = AdminDaoImpl.getInstance().getAdminInfoList();
        System.out.println(list.isEmpty());
        for(Admin i:list)
        {
            System.out.println(i);
        }
    }

    @Test
    public void register()
    {
        boolean b = AdminDaoImpl.getInstance().register("123","123","123");
        System.out.println(b);
    }
}
