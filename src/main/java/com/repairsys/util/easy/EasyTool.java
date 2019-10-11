package com.repairsys.util.easy;

import com.alibaba.fastjson.JSONObject;
import com.repairsys.bean.entity.WTime;
import com.repairsys.bean.entity.Worker;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @Author lyr
 * @create 2019/9/26 1:29
 */
public final class EasyTool {
    private static final Logger logger = LoggerFactory.getLogger(EasyTool.class);


    //分页查询公式  limit (page-1)*size,size

    /**
     * 形如 select * from table limit ?,?; 的sql语句，给写dao的程序员方便计算用的
     *
     * @param nextPage 前端点击下一页，的目标页面
     * @param size     一页要的总的记录数
     * @return 长度为2的 int数组，第一个数是limit的第一个问号，第二个是 limit的第二个问号
     */
    public static int[] getLimitNumber(int nextPage, int size) {
        if (nextPage <= 0) {
            nextPage = 1;
        }
        int[] ans = new int[2];
        ans[0] = (nextPage - 1) * size;
        ans[1] = size;
        return ans;
    }

    /**
     * 传入入会话的request对象，验证用户输入的验证码是否正确
     *
     * @param request 用户的请求
     * @return 验证码是否填写正确
     */
    public static boolean compareToCode(HttpServletRequest request) {
        logger.debug("进入比较");
        JSONObject jsonObject = (JSONObject) request.getAttribute("requestBody");
        logger.debug("获得对象");
        System.out.println(jsonObject);
        String code = jsonObject.getString("vcode").toLowerCase();
        System.out.println(code);
        String ans = (String) request.getSession().getAttribute("CHECKCODE_SERVER");
        System.out.println(ans);
        logger.debug("{}", ans);
        boolean b = false;
        b = ans.equals(code);
        logger.debug("{}", b);
        return b;

    }

    /**
     * 估计bean中的属性，做一个简单的排序，用于实现简单的推荐算法
     * @param workerList 工人或者工人表单的集合
     * @param workerTimeList  工人或者工人时间表类
     */
    public static void resortListOfWorker(List<WTime>workerTimeList,List<Worker> workerList)
    {
        Collections.sort(workerTimeList);
        Collections.sort(workerList);
        Iterator<WTime> it = workerTimeList.iterator();
        for(Worker w:workerList)
        {
            if(it.hasNext())
            {
                WTime table = it.next();
                if(table.getwKey()==w.getwKey())
                {
                    w.setScore(table.getSum());
                }
            }else{
                break;
            }
        }

        /*
        *
        * 这里是使用了 lambda表达式，如果报错，请把程序改成 比较器形式，或者把jdk版本修改为 1.8或以上
        *
        * */
        workerList.sort((Worker i, Worker j) -> {
            return j.getScore() - i.getScore();
        });

    }



}
