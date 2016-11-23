package service;

import dao.ScheduleDao;
import jobs.Job;
import model.RunnableList;
import model.Task_List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import utils.Constants;
import utils.LogUtil;

import java.util.List;


public class Monitor_Thread extends Thread {
    //单例线程
    private static Monitor_Thread thread=new Monitor_Thread();

    public static Monitor_Thread getInstance(){
        return thread;
    }

    private static Logger logger = LogManager.getLogger(Monitor_Thread.class.getName());

    private ScheduleDao scheduleDao;

    private Monitor_Thread(){
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String scheduleDaoBean=(context.getBeanNamesForType(ScheduleDao.class))[0];
        this.scheduleDao= (ScheduleDao) context.getBean(scheduleDaoBean);
    }

    @Override
    public void run() {
        //测试输出
        System.out.println("Monitor_Thread begin");
        try {
            while (true) {

                //获取可跑任务
                List<RunnableList> runnableLists = scheduleDao.queryRunnableList();
                //存在可跑任务
                if (!runnableLists.isEmpty()) {
                    //测试输出
                    System.out.println("Monitor_Thread add");
                    for (RunnableList runnableList : runnableLists) {
                        int result = ScheduleService.checkJob(runnableList.getTask_id()
                                , runnableList.getPara()
                                , runnableList.getTaskclassname());
                        if (result == Constants.FAIL) {
                            continue;
                        }
                        Job job = new Job(runnableList.getTask_id()
                                , new JSONObject(runnableList.getPara())
                                , runnableList.getTaskclassname(), Constants.TASK_READY);
                        ScheduleService.getInstance().add(job);
                        Task_List task_list = new Task_List(runnableList.getTask_id(), Constants.TASK_WAIT);
                        scheduleDao.updateTaskListByTask_List(task_list);
                        LogUtil.SuccessLogAdd(logger
                                , Constants.LOG_INFO
                                , "Monitor_Thread task_id " + runnableList.getTask_id()
                                , Monitor_Thread.class.getName()
                                , true);
                    }
                } else {
                    //测试输出
                    System.out.println("Monitor_Thread skip");
                    sleep(Constants.MONITORSLEEPTIME);
                }

            }
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger
                    , Constants.LOG_INFO
                    , "Monitor_Thread "
                    , Monitor_Thread.class.getName()
                    ,e.getClass().getName()
                    , true);
        }
    }




}
