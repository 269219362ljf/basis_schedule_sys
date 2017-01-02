package basisSchedule.threadService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于保存所有线程池
 */
public class ThreadPool {

    private static ThreadPool threadPool=new ThreadPool();

    public static ThreadPool getInstance(){
        return threadPool;
    }

    //无限制大小的线程池
    public ExecutorService monitorThreadPool= Executors.newCachedThreadPool();
    //定长的线程池，用于一般工作(线程池大小根据运行时核数)，超出大小的工作会的等待
    public  ExecutorService workThreadPool=Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //定长的线程池，用于定时任务
    public  ExecutorService scheduledThreadPool=Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors());
    //单工作线程的线程池，保证所有任务按照指定顺序，用于同步任务
    public  ExecutorService singleThreadPool=Executors.newSingleThreadScheduledExecutor();


}
