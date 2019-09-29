import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.repairsys.util.net.Postman;
import net.sf.json.util.JSONBuilder;
import org.junit.Test;

import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/9/30 1:42
 * 验证是否通过
 */
public class NetTest {
    @Test
    public void loginCall() throws IOException {
        JSONObject jsonObject = Postman.doGet("181543430","yrdddd");
        System.out.println(jsonObject);
        System.out.println(jsonObject.getString("flag"));
    }
}
