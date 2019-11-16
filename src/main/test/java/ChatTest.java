import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.dao.MsgDao;
import com.repairsys.chat.domain.Message;
import com.repairsys.chat.service.MessageServiceImpl;

import com.repairsys.chat.util.MsgSender;
import com.repairsys.chat.util.ServerHandler;
import com.repairsys.code.ChatEnum;

import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 专门用来测试 webSocket 聊天工作室的，请不要加别的业务的代码
 *
 * @Author lyr
 * @create 2019/11/4 2:18
 *
 *
 */
public class ChatTest {

    @Test
    public void list()
    {

        System.out.println(MsgDao.getInstance().countAdminMessage("181543430"));
        System.out.println(MsgDao.getInstance().countStuMessage("admin"));
    }

    @Test
    public void getPage()
    {
        MsgDao p= MsgDao.getInstance();
        p.getMessageOfBoth("admin",true,0,6).forEach(System.out::println);
    }



    @Test
    public void geCnt()
    {
        //这是管理员，要获取学生表写入的信息
        JSONObject jsonObject =
                MsgSender.jsonString().add("target","admin")
                .add("page",1)
                .add("size",10).getJsonObject();

        List<Message> l = MessageServiceImpl.getInstance().getStudentPage(jsonObject);
        System.out.println(l);
    }

    @Test
    public void getCnt()
    {
        //学生获取管理员留言
        JSONObject jsonObject =
                MsgSender.jsonString().add("target","181543430")
                        .add("page",1)
                        .add("size",10).getJsonObject();

        List<Message> l = MessageServiceImpl.getInstance().getAdminPage(jsonObject);
        System.out.println(l);
    }

    @Test
    public void printCnt()
    {
        Object o = MsgDao.getInstance().countAdminMessage("181543430");
        System.out.println(o);
    }

    @Test
    public void getMsg()
    {
        String adminId = "admin";
        //我是管理员，要获取学生留言
        // List<Message> p = MsgDao.getInstance().getStudentMsg(adminId);
        // p.forEach(System.out::println);

    }

    @Test
    public void db()
    {
        JSONObject obj = MsgSender.jsonString().add("sender","lyr")
                .add("receiver","lyr")
                .add("msg","44444556").getJsonObject();
        MessageServiceImpl.getInstance().saveAdminMessage(obj);
    }
    @Test
    public void print()
    {
        String txt =  MsgSender.jsonString().add("msg","123").add("type", ChatEnum.TALK).toString();
        System.out.println(txt);
    }

    @Test
    public void path()
    {
        LinkedBlockingQueue<Integer> q = new LinkedBlockingQueue<>();
        q.poll();
        System.out.println(123);
        q.offer(1);
        Integer t = q.poll();
        System.out.println(t);
        t = q.poll();
        System.out.println(t);



    }


    @Test
    public void dbPrint()
    {
        ServerHandler p = ServerHandler.getInstance();

        p.startService();
        JSONObject t = MsgSender.jsonString().add("t","123").getJsonObject();
        p.msgEnqueue(t);
        try {
            TimeUnit.SECONDS.sleep(5);
            p.adminMessageEnqueue(t);
            TimeUnit.SECONDS.sleep(5);
            p.adminMessageEnqueue(t);
            TimeUnit.SECONDS.sleep(5);
            p.shutDownService();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }



}
