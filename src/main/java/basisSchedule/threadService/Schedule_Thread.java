package basisSchedule.threadService;

import basisSchedule.jobs.Job;
import utils.Constants;



//调度主线程，主要用于调度执行队列，包装jobThread并分配到线程池ThreadPool执行
public class Schedule_Thread extends Thread {
    //单例线程
    private static Schedule_Thread thread=new Schedule_Thread();

    public static Schedule_Thread getInstance(){
        return thread;
    }

    //启动线程池并开始执行任务
    @Override
    public void run() {
            try {
                //循环检查执行任务列表，有可以执行的就进行调度，没有进行休眠
                while(true) {
                    if(!JobsPool.getInstance().checkJobsEmpty()) {
                        //此处是实现调度算法的
                        Job runJob = null;
                        //循环将任务列表分发到线程池
                        while (!JobsPool.getInstance().checkJobsEmpty()) {
                            runJob = ScheduleTask.getjob();
                            if(runJob ==null){
                                continue;
                            }
                            //获取任务后从任务列表中摘除
                            JobsPool.getInstance().removeJob(runJob.getTask_id());
                            //建立任务线程
                            JobThread jobThread = new JobThread(runJob);
                            //线程池执行任务
                            ThreadPool.getInstance().execute(jobThread,runJob.getTask_type());
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
}
