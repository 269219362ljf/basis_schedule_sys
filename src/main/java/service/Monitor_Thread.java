package service;

import jobs.Job;
import schedule.JobThread;
import utils.Constants;

import java.util.List;
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
        //新建线程池
        ForkJoinPool pool = new ForkJoinPool(Constants.THREADCOUNT);

            try {
                //此处是实现调度算法的
//                Job runJob=null;
//                runJob= ScheduleTask.getjob();
                List<Job> jobs=ScheduleService.getInstance();
                for (Job runjob : jobs) {
                    JobThread jobThread = new JobThread(runjob);
                    pool.execute(jobThread);
                    //pool.execute(new MonitorTask());
                }
            }
            catch(Exception e){
                e.printStackTrace();
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
