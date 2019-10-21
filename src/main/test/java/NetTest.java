import com.alibaba.fastjson.JSONObject;
import com.repairsys.util.net.Postman;
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
        JSONObject jsonObject = null;
        try {
            jsonObject = Postman.doPost("181549422", "huxi9138");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonObject.getString("userrealname"));

    }
}
