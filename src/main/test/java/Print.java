import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.Admin;
import com.repairsys.bean.entity.Form;
import com.repairsys.dao.DaoFactory;
import com.repairsys.dao.impl.admin.AdminDaoImpl;
import com.repairsys.dao.impl.form.FormDaoImpl;
import com.repairsys.dao.impl.worker.WorkerDaoImpl;
import com.repairsys.service.FormService;
import com.repairsys.service.impl.form.FormServiceImpl;
import com.repairsys.util.mail.MailUtil;
import com.repairsys.util.md5.Md5Util;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author lyr, Prongs
 * @date 2019/9/22
 * 这个是junit包，试错
 */

public class Print {
    @Test
    public void printSome() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        System.out.println("更新数据库时间："+c.get(Calendar.YEAR)+"-"+(1+c.get(Calendar.MONTH))+"-"+c.get(Calendar.DATE)+" " +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":" +c.get(Calendar.SECOND));

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
        boolean b = AdminDaoImpl.getInstance().registerPlus("123", "dd", pwd);
        System.out.println(b);
    }

    @Test
    public void md5() {
        String pwd = "少冰少糖加椰果";
        String newPwd = Md5Util.getMd5(pwd);
        System.out.println(newPwd);

    }

    @Test
    public void login() {
        Admin admin = AdminDaoImpl.getInstance().login("123", "123");
        System.out.println(admin.toString());
    }

    @Test
    public void sendMailTest() throws Exception {
        MailUtil.sendFinishMail("798237844@qq.com");
    }

    @Test
    public void register2() {
        boolean b = AdminDaoImpl.getInstance().registerPlus("12d3", "123", "123");
        System.out.println(b);
    }

    /**
     * 管理员加密登录
     */
    @Test
    public void login2() {
        Admin a = AdminDaoImpl.getInstance().login("123", "123");
        System.out.println(a != null);
        System.out.println(a.getAdminName());
        System.out.println(a.getAdminPassword());
    }

    @Test
    public void get() {
        List<Form> form = FormDaoImpl.getInstance().queryByStudentId("181");
        System.out.println(form);
    }

    @Test
    public void printDate() {
        java.util.Date d = new java.util.Date();
        System.out.println(d);
    }

    @Test
    public void printJson() {
        JSONObject jsonObject = JSONObject.parseObject("{'stuId':'123','stuPassword':'1'}");
        System.out.println(jsonObject.getString("stuId"));
        System.out.println(jsonObject);
    }

    @Test
    public void totalCount() {
        FormService formService = new FormServiceImpl();
        int i = formService.getTotalCount();
        System.out.println(i);
    }

    @Test
    public void printUUID()
    {
        String y =  UUID.randomUUID().toString();
        System.out.println(y);
    }
    @Test
    public void printPerson()
    {
        com.repairsys.chat.bean.Admin admin = new com.repairsys.chat.bean.Admin();
        admin.append("123");
        admin.append("12222");
        admin.append("444");
        System.out.println(admin.getTargetSet());
        String text = "{ 'list':" +admin.getTargetSet()+"}";
        System.out.println(text);

    }
}
