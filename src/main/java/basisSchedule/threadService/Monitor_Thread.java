package basisSchedule.threadService;

import basisSchedule.resultModel.Task;

import basisSchedule.sqlDao.ScheduleDao;

import basisSchedule.resultModel.Task_List;

import common.service.ScheduleCommonService;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.List;

//监控线程，负责将新任务放入执行队列和检查任务是否正确完整
public class Monitor_Thread extends Thread {
    //单例线程
    private static Monitor_Thread thread=new Monitor_Thread();

    public static Monitor_Thread getInstance(){
        return thread;
    }


    private ScheduleDao scheduleDao;

    private ScheduleCommonService scheduleCommonService;

    private Monitor_Thread(){
        scheduleDao=(ScheduleDao)CommonUtil.getBean(ScheduleDao.class);
        scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
    }

    @Override
    public void run() {
        try {
            while (true) {

                //获取可跑任务
                List<Task> runnableLists = scheduleDao.queryRunnableList();
                //存在可跑任务
                if (!runnableLists.isEmpty()) {
                    for (Task runnableTask : runnableLists) {
                        //检查任务是否已在待执行列表中
                        if (JobsPool.getInstance().containJobs(runnableTask.getTask_id())) {
                            continue;
                        }
                        //增加到可执行列表
                        if(JobsPool.getInstance().addJob2Jobs(
                                runnableTask.getTask_id(),runnableTask.getPara(),runnableTask.getTaskclassname(),runnableTask.getType())
                                ==Constants.FAIL){
                            continue;
                        };
                        //更改状态为等待
                        Task_List task_list = new Task_List(runnableTask.getTask_id(), Constants.TASK_WAIT);
                        task_list.setT_date(CommonUtil.date2string8(new Date()));
                        scheduleCommonService.update(task_list);
                        LogUtil.SuccessLogAdd( Constants.LOG_INFO
                                , "Monitor_Thread task_id " + runnableTask.getTask_id()
                                , Monitor_Thread.class.getName()
                                , true);
                    }
                } else {
                    sleep(Constants.MONITORSLEEPTIME);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd( Constants.LOG_INFO
                    , "Monitor_Thread "
                    , Monitor_Thread.class.getName()
                    ,e.getClass().getName()
                    , true);
        }
    }
}
