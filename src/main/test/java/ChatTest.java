import com.repairsys.chat.util.MsgSender;
import com.repairsys.code.ChatEnum;
import com.repairsys.util.textfilter.TextFilterFactory;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

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
    public void print()
    {
        String txt =  MsgSender.jsonString().add("msg","123").add("type", ChatEnum.TALK).toString();
        System.out.println(txt);
    }

    @Test
    public void path()
    {
        String p = TextFilterFactory.getPath();
        System.out.println(p);

    }



}
