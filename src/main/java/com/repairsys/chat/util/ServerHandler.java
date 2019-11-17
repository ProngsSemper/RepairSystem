package com.repairsys.chat.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.repairsys.chat.domain.Message;
import com.repairsys.chat.service.MessageServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Author lyr
 * @create 2019/11/4 2:32
 *
 * 事务处理类，专门用于封装一下奇奇怪怪的代码
 *
 */
public class ServerHandler {
    private static final ServerHandler SERVER = new ServerHandler();
    /**
     * 处理查询数据库的 service层
     */
    private static final MessageServiceImpl dbService = MessageServiceImpl.getInstance();

    private ServerHandler(){

    }

    public static ServerHandler getInstance(){return SERVER;}
    private boolean running = false;


    private static final Logger logger = LoggerFactory.getLogger(ServerHandler.class);

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private static final ExecutorService executorService = new ThreadPoolExecutor(3,3,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>(),NAMED_THREAD_FACTORY);


    /**
     * 存放离线消息
     */
    private  final LinkedBlockingQueue<JSONObject> MSG_QUEUE = new LinkedBlockingQueue<>();

    /**
     * 存放管理员离线消息
     */
    private  final LinkedBlockingQueue<JSONObject> ADMIN_MSG_QUEUE = new LinkedBlockingQueue<>();


    /**
     * 修改已经读取了的消息的状态
     */
    private final LinkedBlockingQueue<JSONObject> UPDATE_MESSAGE_QUEUQ = new LinkedBlockingQueue<>();


    /**
     * @param jsonObject 学生客户端发送的json数据
     */
    public void msgEnqueue(JSONObject jsonObject)
    {
        logger.info("学生消息入队 {}",jsonObject);

        MSG_QUEUE.offer(jsonObject);
    }

    /**
     * @param jsonObject 管理员客户端发送的 json数据
     */
    public void adminMessageEnqueue(JSONObject jsonObject)
    {
        logger.info("管理员消息入队{}",jsonObject);
        ADMIN_MSG_QUEUE.offer(jsonObject);
    }


    public String getStudentMessage(JSONObject jsonObject)
    {
        //转 json对象
        List<Message> list = dbService.getStudentPage(jsonObject);
        jsonObject.put("messageList",list);
        return JSONObject.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd/hh:mm:ss ", SerializerFeature.WriteDateUseDateFormat);
    }
    public String getAdminMessage(JSONObject jsonObject)
    {
        jsonObject.put("messageList",dbService.getAdminPage(jsonObject));
        //转 json对象
        return JSONObject.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd/hh:mm:ss ", SerializerFeature.WriteDateUseDateFormat);

    }
    //FIXME: 预备方案
    public String getMessageOfBoth(JSONObject jsonObject,boolean isAdmin)
    {
        List<Message> list = dbService.getMessageOfBoth(jsonObject,isAdmin);
        //todo: 写回数据库
        jsonObject.put("messageList",list);
        jsonObject.put("isAdmin",isAdmin);
        //处理已经读取了的消息
        UPDATE_MESSAGE_QUEUQ.offer(jsonObject);
        //转 json对象
        return JSONObject.toJSONStringWithDateFormat(jsonObject, "yyyy-MM-dd/hh:mm:ss ", SerializerFeature.WriteDateUseDateFormat);

    }


    /**
     * 开启多线程处理聊天事务
     */
    public void startService()
    {
        if(this.running)
        {
            return;
        }
        this.running = true;
        executorService.submit(new AdminTask());
        executorService.submit(new StudentTask());
        executorService.submit(new UpdateTask());
    }


    /**
     * 关闭线程池
     */
    public void shutDownService()
    {
        if(!executorService.isShutdown())
        {
            executorService.shutdownNow();
        }

    }

    /*
    *
    * 下面是消息处理线程
    *
    *
    * */

    /**
     * 负责工人信息写进数据库
     */
    private static class AdminTask implements Runnable {

        @Override
        public void run() {

            try {
                Queue<JSONObject> queue = ServerHandler.SERVER.ADMIN_MSG_QUEUE;
                while (!Thread.currentThread().isInterrupted())
                {

                    while (!queue.isEmpty())
                    {
                        dbService.saveAdminMessage(queue.poll());
                    }
                    logger.debug("进入睡眠");

                    TimeUnit.SECONDS.sleep(7);
                }
                while (!queue.isEmpty())
                {
                    dbService.saveAdminMessage(queue.poll());
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }


        }
    }

    /**
     * 学生信息写进数据库
     */
    private static class StudentTask implements Runnable {

        @Override
        public void run() {
            try {
                Queue<JSONObject> queue = ServerHandler.SERVER.MSG_QUEUE;
                while (!Thread.currentThread().isInterrupted())
                {
                    while (!queue.isEmpty())
                    {
                        dbService.saveMessage(queue.poll());
                    }
                    logger.debug("进入睡眠");

                    TimeUnit.SECONDS.sleep(7);
                }

                while (!queue.isEmpty())
                {
                    dbService.saveMessage(queue.poll());
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }


    /**
     * 聊天信息发给前端，前端查看到了聊天信息，就要将未读的改为已读的，放到线程队列里面执行
     */
    private static class UpdateTask implements Runnable {

        @Override
        public void run() {
            try {
                Queue<JSONObject> queue = ServerHandler.SERVER.UPDATE_MESSAGE_QUEUQ;
                while (!Thread.currentThread().isInterrupted())
                {
                    while (!queue.isEmpty())
                    {
                        dbService.updateTalkInformation(queue.poll());
                    }
                    logger.debug("进入睡眠");

                    TimeUnit.SECONDS.sleep(7);
                }

                while (!queue.isEmpty())
                {
                    dbService.updateTalkInformation(queue.poll());
                }



            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }










}
