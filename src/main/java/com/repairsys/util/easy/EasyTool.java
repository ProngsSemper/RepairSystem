package com.repairsys.util.easy;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author lyr
 * @create 2019/9/26 1:29
 */
public final class EasyTool {

    //分页查询公式  limit (page-1)*size,size

    /**
     *  形如 select * from table limit ?,?; 的sql语句，给写dao的程序员方便计算用的
     *
     * @param nextPage 前端点击下一页，的目标页面
     * @param size 一页要的总的记录数
     * @return  长度为2的 int数组，第一个数是limit的第一个问号，第二个是 limit的第二个问号
     */
    public static int[] getLimitNumber(int nextPage,int size)
    {
        if(nextPage<=0)
        {
            nextPage = 1;
        }
        int[] ans = new int[2];
        ans[0] = (nextPage-1)*size;
        ans[1] = size;
        return ans;
    }


    /**
     * 传入入会话的request对象，验证用户输入的验证码是否正确
     * @param request 用户的请求
     * @return 验证码是否填写正确
     */
    public static boolean compareToCode(HttpServletRequest request)
    {
        JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
        String code = jsonObject.getString("code").toLowerCase();
        String ans = (String)request.getSession().getAttribute("CHECKCODE_SERVER");
        return ans.equals(code);

    }



}
