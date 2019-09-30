package com.repairsys.util.net;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/9/30 0:29
 */
public class Postman {

    private static final String LOGIN_REQUEST="http://jwxt.gduf.edu.cn/app.do?method=authUser&";
    private static final int PASS=200;


    public static JSONObject doGet(String xh,String pwd) throws IOException {
        String finalUrl = LOGIN_REQUEST+"xh="+xh+"&pwd="+pwd;
        // System.out.println(finalUrl);

        HttpGet getHandler = new HttpGet(finalUrl);
        //创建一个用于抓包的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //提交并且获取url的响应
        CloseableHttpResponse response = httpClient.execute(getHandler);
        // System.out.println(response);

        return getJsonObject(response);
    }
    private static final JSONObject getJsonObject(CloseableHttpResponse response) throws IOException {
        if((response.getStatusLine().getStatusCode()!=PASS))
        {
            return null;
        }
        HttpEntity entity = response.getEntity();
        String jsonContent = EntityUtils.toString(entity,"utf-8");
        JSONObject jsonObject = JSONObject.parseObject(jsonContent);
        return jsonObject;

    }
}
