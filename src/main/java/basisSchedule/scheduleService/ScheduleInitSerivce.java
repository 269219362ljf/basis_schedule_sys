package basisSchedule.scheduleService;

import basisSchedule.resultModel.Task;
import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import basisSchedule.resultModel.Task_List;
import basisSchedule.threadService.JobsPool;
import basisSchedule.threadService.Monitor_Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import basisSchedule.threadService.Schedule_Thread;
import utils.CommonUtil;
import utils.Constants;

import java.util.Date;
import java.util.List;


@Service
public class ScheduleInitSerivce {

    private static volatile boolean isNotInit=true;

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScheduleDao scheduleDao;

    //初始化执行执行任务列表
    private int initJobs(){

        int result;
        if(isNotInit) {
            //初始化任务列表
            result=scheduleDao.initTaskList(CommonUtil.date2string8(new Date()));
            if(result==Constants.FAIL){
                return Constants.FAIL;
            }
            //更新任务列表状态
            result=scheduleDao.updateAllTaskList();
            if(result==Constants.FAIL){
                return Constants.FAIL;
            }
            isNotInit=false;
        }
        return Constants.SUCCESS;
    }

    //整体调度初始化
    public int init(){
        //初始化任务列表，中途加入的任务也会初始化进去
        int result=initJobs();
        if(result==Constants.FAIL){
            return Constants.FAIL;
        }
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
