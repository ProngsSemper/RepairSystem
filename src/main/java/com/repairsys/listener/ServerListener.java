package com.repairsys.listener;
/**
 * @Author lyr
 * @create 2019/10/17 13:33
 */

import com.repairsys.dao.impl.agenda.WorkerScheule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.servlet.http.HttpSessionBindingEvent;
import java.util.Calendar;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@WebListener()
public class ServerListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {





    //线程运行状态标签

    private final ThreadPoolExecutor pool =
            new ThreadPoolExecutor(1,2,60, TimeUnit.SECONDS,new LinkedBlockingDeque<>(2));

    public ServerListener() {

    }


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Runnable r = new Runnable() {
            @Override
            public void run() {

                Logger logger = LoggerFactory.getLogger(this.getClass());
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

                        logger.warn("关闭线程成功");


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
    public void contextDestroyed(ServletContextEvent sce) {

        this.pool.shutdownNow();

    }


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        /* Session is created. */

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        /* Session is destroyed. */

    }

     @Override
    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent sbe) {

    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent sbe) {

    }
}