package service;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

/**
 * Created by lu on 2016/11/10.
 */
//监控任务线程，主要用于启动调度模块，为调度模块主入口
public class Monitor_Thread extends Thread {
    //单例线程
    private static Monitor_Thread thread=new Monitor_Thread();

    public static Monitor_Thread getInstance(){
        return thread;
    }

    //启动线程池并开始执行任务
    @Override
    public void run() {
        //休眠时长
        int sleeptime = 1000;
        //线程长度
        int threadcount = 50;
        ForkJoinPool pool = new ForkJoinPool(threadcount);
        while (true) {
            try {
                //新建任务
                MonitorTask task=new MonitorTask();
                //放入线程池执行
                pool.execute(task);

            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    //测试用任务类
    private class MonitorTask extends RecursiveAction {

        @Override
        protected void compute() {
            System.out.println("test");
        }
    }

}
