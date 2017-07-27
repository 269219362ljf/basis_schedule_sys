package basisSchedule.threadService;

import basisSchedule.jobs.Job;
import basisSchedule.resultModel.Task_List;
import common.service.ScheduleCommonService;
import utils.CommonUtil;
import utils.Constants;

import java.util.Date;


//调度主线程，主要用于调度执行队列，包装jobThread并分配到线程池ThreadPool执行
public class Schedule_Thread extends Thread {
    //单例线程
    private static Schedule_Thread thread=new Schedule_Thread();

    public static Schedule_Thread getInstance(){
        return thread;
    }

    private ScheduleCommonService scheduleCommonService;

    //启动线程池并开始执行任务
    @Override
    public void run() {
            try {
                scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
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
                            //记录开始时间
                            Date begin=new Date();
                            //任务记录新建
                            Task_List task_list=new Task_List(runJob.getTask_id(),Constants.TASK_RUNNING);
                            task_list.setT_date(runJob.getJob_date());
                            task_list.setBeg_time(begin);
                            //更新数据库任务列表状态
                            scheduleCommonService.update(task_list);
                            //更新状态后从任务列表中摘除
                            JobsPool.getInstance().removeJob(runJob.getTask_id());
                            //建立任务线程
                            JobThread jobThread = new JobThread(runJob);
                            //线程池执行任务
                            //ThreadPool.getInstance().execute(jobThread);
                            jobThread.run();
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
