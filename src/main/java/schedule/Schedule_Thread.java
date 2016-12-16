package schedule;

import jobs.Job;
import service.ScheduleService;
import utils.Constants;


import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;


//监控任务线程，主要用于启动调度模块，为调度模块主入口
public class Schedule_Thread extends Thread {
    //单例线程
    private static Schedule_Thread thread=new Schedule_Thread();

    public static Schedule_Thread getInstance(){
        return thread;
    }

    //启动线程池并开始执行任务
    @Override
    public void run() {
        //新建线程池
        ForkJoinPool pool = new ForkJoinPool(Constants.THREADCOUNT);

            try {
                //循环检查执行任务列表，有可以执行的就进行调度，没有进行休眠
                while(true) {
                    if(!ScheduleService.checkJobsEmpty()) {
                        //此处是实现调度算法的
                        Job runJob = null;
                        while (!ScheduleService.checkJobsEmpty()) {
                            runJob = ScheduleTask.getjob();
                            if(runJob ==null){
                                continue;
                            }
                            //获取任务后从任务列表中摘除
                            ScheduleService.removeJobsByTask_id(runJob.getTask_id());
                            JobThread jobThread = new JobThread(runJob);
                            pool.execute(jobThread);
                        }
                    }else{
                        sleep(Constants.SLEEPTIME);
                    }
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
