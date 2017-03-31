package basisSchedule.threadService;

import utils.Constants;

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

    //线程池执行方法
    public void execute(JobThread jobThread){
        execute(jobThread,Constants.TASK_NORMAL);
    }

    public void execute(JobThread jobThread,int task_type){
        switch(task_type){
            case Constants.TASK_HURRY:monitorThreadPool.execute(jobThread);return;
            case Constants.TASK_NORMAL:workThreadPool.execute(jobThread);return;
            case Constants.TASK_SCHEDULE:scheduledThreadPool.execute(jobThread);return;
            case Constants.TASK_SINGLE:singleThreadPool.execute(jobThread);return;
            default:workThreadPool.execute(jobThread);return;
        }



    }



}
