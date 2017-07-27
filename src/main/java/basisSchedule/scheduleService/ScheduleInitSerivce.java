package basisSchedule.scheduleService;

import basisSchedule.jobs.InitJob;
import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.threadService.Monitor_Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import basisSchedule.threadService.Schedule_Thread;
import utils.Constants;


@Service
public class ScheduleInitSerivce {

    @Autowired
    private ScheduleDao scheduleDao;


    //整体调度初始化
    public int init(){
        //初始化平台参数
        Constants.init();

        //将所有问题任务初始化为状态0
        scheduleDao.updateAllTaskList();
        //进入调度主入口，若调度已启动，忽略
        Schedule_Thread schedule_thread=Schedule_Thread.getInstance();
        if(!schedule_thread.isAlive()){
            schedule_thread.start();
        }

        //进入监控主入口，若监控已启动，忽略
        Monitor_Thread monitor_thread=Monitor_Thread.getInstance();
        if(!monitor_thread.isAlive()){
            monitor_thread.start();
        }
        return Constants.SUCCESS;
    }


}
