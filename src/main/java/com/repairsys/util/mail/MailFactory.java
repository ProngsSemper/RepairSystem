package com.repairsys.util.mail;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * @author Prongs
 * @date 2019/11/16 11:20
 * 发送邮件线程池，避免一次性发送太多邮件导致错误
 * 一次性最多发送一百封邮件，然后睡眠10秒
 */

public class MailFactory {
    private LinkedBlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    private static final ThreadFactory NAMED_THREAD_FACTORY = new ThreadFactoryBuilder().setNameFormat("thread-call-runner-%d").build();

    private final ExecutorService executorService = new ThreadPoolExecutor(3, 3, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), NAMED_THREAD_FACTORY);
    private final ExecutorService consumerThread = Executors.newSingleThreadExecutor();

    private MailFactory() {
    }

    public static MailFactory getInstance() {
        return single;
    }

    public LinkedBlockingQueue getQueue() {
        return taskQueue;
    }

    private static MailFactory single = new MailFactory();

    public void checkAndRun() {
        consumerThread.execute(this::consume);

    }

    private void consume() {

        int flag = 101;
        while (!taskQueue.isEmpty()) {
            int i = 0;
            while (++i < flag && !taskQueue.isEmpty()) {

                Runnable p = taskQueue.poll();
                executorService.submit(p);

            }
            try {

                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    public void shutDown() {
        this.executorService.shutdown();

    }

}
