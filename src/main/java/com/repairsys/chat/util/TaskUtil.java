package com.repairsys.chat.util;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.repairsys.chat.ChatServer;
import com.repairsys.chat.domain.User;
import com.repairsys.code.ChatEnum;

import java.util.Map;
import java.util.concurrent.*;


/**
 * @Author lyr
 * @create 2019/11/17 15:33
 */
public final class TaskUtil {

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private static final ExecutorService executorService = new ThreadPoolExecutor(3,3,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),NAMED_THREAD_FACTORY);

    private static final TaskUtil UTIL = new TaskUtil();
    private TaskUtil(){}
    public static TaskUtil getInstance(){return UTIL;}

    public void shutDown()
    {
        executorService.shutdownNow();
    }

    public static class ConsumerTask implements Runnable
    {
        /**
         * 这是发送消息的 任务
         */
        private JSONObject production;

        public ConsumerTask(JSONObject production) {
            this.production = production;
        }
        //fixme: 要区分 type 决定消息类型

        @Override
        public void run() {
            // production.put("info", 1);
            production.put("type",ChatEnum.IMG_TASK.getCode());
            String target = production.getString("target");
            boolean b = production.getBoolean("isAdmin");
            Map[] map = ChatServer.getChatServerMap();

            if(b)
            {
                //如果是管理员，发给学生
                User u = (User) map[1].get(target);
                if(u!=null)
                {
                    //在线发送给学生
                    u.receive(production);
                }
                //异步离线写入数据库
                ServerHandler.getInstance().adminMessageEnqueue(production);


            }else{
                //如果是学生，发给管理员
                User u = (User) map[0].get(target);
                if(u!=null)
                {
                    u.receive(production);
                }
                ServerHandler.getInstance().msgEnqueue(production);

            }
        }
    }

    public void consume(JSONObject jsonObject)
    {
        executorService.submit(new ConsumerTask(jsonObject));
    }



}
