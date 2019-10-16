package com.repairsys.controller.clock;

import com.repairsys.controller.BaseServlet;
import com.repairsys.dao.impl.agenda.WorkerScheule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

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

    private final ThreadPoolExecutor pool =
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
                Calendar c = Calendar.getInstance();
                while (!Thread.currentThread().isInterrupted())
                {
                    // //更新数据库

                    WorkerScheule.getInstance().cleanAndUpdateTable();
                    logger.warn("自动更新线程");



                    logger.warn("更新数据库时间："+c.get(Calendar.YEAR)+"-"+(1+c.get(Calendar.MONTH))+"-"+c.get(Calendar.DATE)+" " +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":" +c.get(Calendar.SECOND));

                    try {

                        //让线程睡两个小时

                        Thread.sleep(3600000*2);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }

                logger.warn("线程成功中断并结束 -- 时间： "+c.get(Calendar.YEAR)+"-"+(1+c.get(Calendar.MONTH))+"-"+c.get(Calendar.DATE)+" " +c.get(Calendar.HOUR_OF_DAY)+":"+c.get(Calendar.MINUTE)+":" +c.get(Calendar.SECOND));
            }
        };
        pool.execute(r);
        pool.shutdown();
    }


    @Override
    public void destroy() {

        logger.debug("尝试线程关闭");
        this.pool.shutdownNow();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        super.destroy();
    }
}
