package com.repairsys.controller.clock;

import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 接收到请求，开启线程，自动清理并更新数据库
 * @Author lyr
 * @create 2019/10/15 22:22
 */
@WebServlet("/server/clock")
public class ClockServer extends BaseServlet {
    private static final Logger logger = LoggerFactory.getLogger(ClockServer.class);
    private boolean running = false;
    //线程运行状态标签

    private static final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(1,2,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2));
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //管理员登录那天，一登录就发送请求到这里，然后服务器开启更新线程，每小时自动更新数据库里面的表单
        if(!this.running)
        {
            synchronized (this)
            {
                if(!this.running)
                {
                    logger.info("clockServer 收到请求，并开启了线程");
                    this.running = true;
                    this.update();
                }
            }
        }

        super.doPost(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    /** 这个小项目直接开一个线程就好了，但是为了接触新的知识，尝试使用线程池 */
    private void update()
    {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true)
                {
                    // //更新数据库
                    // System.out.println(123);
                    WorkerScheule.getInstance().cleanAndUpdateTable();
                    logger.trace("自动更新线程");

                    Calendar c = Calendar.getInstance();

                    logger.trace("更新数据库时间："+c.get(Calendar.YEAR)+"-"+(1+c.get(Calendar.MONTH))+"-"+c.get(Calendar.DATE)+" " +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":" +c.get(Calendar.SECOND));

                    try {

                        Thread.sleep(3600000*2);
                        //睡一2个小时，自动唤醒
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        pool.execute(r);


    }
}
