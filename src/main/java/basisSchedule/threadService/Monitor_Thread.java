package basisSchedule.threadService;

import basisSchedule.resultModel.Task;

import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.tablesDao.Task_ListDao;

import basisSchedule.resultModel.Task_List;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;
import utils.ScheduleUtil;

import java.util.Date;
import java.util.List;


public class Monitor_Thread extends Thread {
    //单例线程
    private static Monitor_Thread thread=new Monitor_Thread();

    public static Monitor_Thread getInstance(){
        return thread;
    }


    private ScheduleDao scheduleDao;

    private Task_ListDao task_listDao;

    private Monitor_Thread(){
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String scheduleDaoBean=(context.getBeanNamesForType(ScheduleDao.class))[0];
        this.scheduleDao= (ScheduleDao) context.getBean(scheduleDaoBean);
        String task_listDaoBean=(context.getBeanNamesForType(Task_ListDao.class))[0];
        this.task_listDao= (Task_ListDao) context.getBean(task_listDaoBean);
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
                        //检查任务数据是否正确
                        int result = ScheduleUtil.checkJob(runnableTask.getTask_id()
                                , runnableTask.getPara()
                                , runnableTask.getTaskclassname());
                        //检查任务是否已在待执行列表中，任务数据是否正确
                        if (result == Constants.FAIL
                                || JobsPool.getInstance().checkIsInRunnableList(runnableTask.getTask_id())) {
                            continue;
                        }
                        //增加到可执行列表
                        JobsPool.getInstance().addJob2Jobs(
                                runnableTask.getTask_id(),runnableTask.getPara(),runnableTask.getTaskclassname(),runnableTask.getType());
                        //更改状态为等待
                        Task_List task_list = new Task_List(runnableTask.getTask_id(), Constants.TASK_WAIT);
                        task_list.setT_date(CommonUtil.date2string8(new Date()));
                        task_listDao.updateTaskListByTask_List(task_list);
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
