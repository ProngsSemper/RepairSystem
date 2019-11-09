import com.alibaba.fastjson.JSONObject;
import com.repairsys.chat.service.MessageServiceImpl;
import com.repairsys.chat.util.MsgSender;
import com.repairsys.chat.util.ServerHandler;
import com.repairsys.code.ChatEnum;
import com.repairsys.util.textfilter.TextFilterFactory;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.slf4j.LoggerFactory;

import javax.json.JsonObject;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.LinkedHashSet;
import java.util.Set;
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
