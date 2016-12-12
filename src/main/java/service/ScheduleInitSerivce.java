package service;

import dao.ScheduleDao;
import dao.TaskDao;
import dao.Task_ListDao;
import model.RunnableList;
import model.Task_List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import schedule.Schedule_Thread;
import utils.Constants;

import java.util.List;


@Service
public class ScheduleInitSerivce {

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScheduleDao scheduleDao;

    //初始化执行执行任务列表
    private int initJobs(){
        int able2run=-1;
        //检查之前任务是否存在错误
        able2run=scheduleDao.queryBeforeErrorCount();
        if(able2run>0 || able2run<0){
            return Constants.BEFOREERRORXIST;
        }
        //初始化任务列表
        int result=scheduleDao.initTaskList();
        if(result==Constants.FAIL){
            return Constants.FAIL;
        }
        result=scheduleDao.updateAllTaskList();
        if(result==Constants.FAIL){
            return Constants.FAIL;
        }

        //初始化系统执行任务列表
        List<RunnableList> runnableLists=scheduleDao.queryRunnableList();
        ScheduleService.getInstance().clear();
        for(RunnableList runnableList:runnableLists){
            ScheduleService.addJob2Jobs(runnableList.getTask_id(),runnableList.getPara(),runnableList.getTaskclassname());
            //更改任务列表状态
            Task_List jobTask=new Task_List(runnableList.getTask_id(),2);
            task_listDao.updateTaskListByTask_List(jobTask);
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
