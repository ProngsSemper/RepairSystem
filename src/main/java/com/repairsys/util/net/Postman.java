package com.repairsys.util.net;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Author lyr
 * @create 2019/9/30 0:29
 */
public final class Postman {

    private static final String LOGIN_REQUEST = "http://jwxt.gduf.edu.cn/app.do?method=authUser&";
    private static final int PASS = 200;
    private static final Logger logger = LoggerFactory.getLogger(Postman.class);

    /**
     * 没有进行超时处理的方法,不安全的方法
     *
     * @param xh
     * @param pwd
     * @return
     * @throws IOException 超时没有得到响应会抛出异常
     * @deprecated
     */
    @Deprecated
    public static JSONObject doGet(String xh, String pwd) throws IOException {
        String finalUrl = LOGIN_REQUEST + "xh=" + xh + "&pwd=" + pwd;

        HttpGet getHandler = new HttpGet(finalUrl);
        //创建一个用于抓包的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //提交并且获取url的响应
        CloseableHttpResponse response = httpClient.execute(getHandler);

        return getJsonObject(response);
    }

    private static JSONObject getJsonObject(CloseableHttpResponse response) throws IOException {
        if ((response.getStatusLine().getStatusCode() != PASS)) {
            return null;
        }
        HttpEntity entity = response.getEntity();
        String jsonContent = EntityUtils.toString(entity, "utf-8");
        JSONObject jsonObject = null;
        jsonObject = JSONObject.parseObject(jsonContent);
        return jsonObject;

    }

    /**
     * 由于 httpClient 是阻塞态的，进行了超时处理方法，安全的方法
     *
     * @param xh  学生账号
     * @param pwd 学生密码
     * @return JSONObject
     * @throws IOException
     */

    public static JSONObject doPost(String xh, String pwd) throws IOException {
        String finalUrl = LOGIN_REQUEST + "xh=" + xh + "&pwd=" + pwd;

        HttpGet getHandler = new HttpGet(finalUrl);
        //创建一个用于抓包的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时处理，不然会一直阻塞读取
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();

        getHandler.setConfig(requestConfig);

        //提交并且获取url的响应
        CloseableHttpResponse response = httpClient.execute(getHandler);

        return getJsonObject(response);
    }

    /**
     * 发送请求提醒服务器改更新一下数据库了
     *
     * @param request 请求
     */
    public static void call(HttpServletRequest request) {

        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
        logger.debug("内部发送请求" + basePath);

        HttpGet getHandler = new HttpGet(basePath + "/server/clock");

        //创建一个用于抓包的客户端
        CloseableHttpClient httpClient = HttpClients.createDefault();

        //设置超时处理，不然会一直阻塞读取
        //设置请求和传输超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(4000).setConnectTimeout(4000).build();

        getHandler.setConfig(requestConfig);

        //提交并且获取url的响应
        try {

            CloseableHttpResponse response = httpClient.execute(getHandler);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
