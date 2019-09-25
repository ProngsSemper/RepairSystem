import com.repairsys.bean.entity.Admin;
import com.repairsys.dao.impl.AdminDaoImpl;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.md5.Md5Util;
import org.junit.Test;

import java.util.List;

/**
 * @author lyr,Prongs
 * @date 2019/9/22
 * 这个是junit包，试错
 */

public class Print {
    @Test
    public void printSome() {
        System.out.println("hello");

    }

    @Test
    public void say() {
        String sql = "select * from administrators";
        List<Admin> list = AdminDaoImpl.getInstance().getAdminInfoList();
        System.out.println(list.isEmpty());
        for (Admin i : list) {
            System.out.println(i);
        }
    }

    @Test
    public void register() {
        String pwd = "123";
        String md5Pwd = Md5Util.getMd5(pwd);
        boolean b = AdminDaoImpl.getInstance().register("123", "123", md5Pwd);
        System.out.println(b);
    }

    @Test
    public void md5() {
        String pwd = "PASSWORD";
        String newPwd = Md5Util.getMd5(pwd);
        System.out.println(newPwd);

    }

    @Test
    public void login() {
        String pwd = "123";
        String md5Pwd = Md5Util.getMd5(pwd);
        Admin admin = AdminDaoImpl.getInstance().login("123", md5Pwd);
        System.out.println(admin.toString());
    }

    @Test
    public void sendMailTest() throws Exception {
        MailUtil.sendMail("798237844@qq.com");
    }
}
