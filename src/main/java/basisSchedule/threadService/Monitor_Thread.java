package basisSchedule.threadService;

import basisSchedule.jobs.Job;
import basisSchedule.resultModel.T_param;
import basisSchedule.resultModel.Task;

import basisSchedule.sqlDao.ScheduleDao;

import basisSchedule.resultModel.Task_List;

import common.service.ScheduleCommonService;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;

//监控线程，负责将新任务放入执行队列和检查任务是否正确完整
public class Monitor_Thread extends Thread {
    //单例线程
    private static Monitor_Thread thread=new Monitor_Thread();

    public static Monitor_Thread getInstance(){
        return thread;
    }


    private ScheduleDao scheduleDao;

    private ScheduleCommonService scheduleCommonService;

    private boolean newFinish_datehasInit=false;

    private Monitor_Thread(){
        scheduleDao=(ScheduleDao)CommonUtil.getBean(ScheduleDao.class);
        scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
    }

    @Override
    public void run() {
        try {
            while (true) {
                //获取可跑任务
                List<Map<String,Object>> runnableLists = scheduleDao.queryRunnableList();
                //存在可跑任务
                if (!runnableLists.isEmpty()) {
                    for (Map<String,Object> temp : runnableLists) {
                        Task runnableTask=new Task();
                        runnableTask.setTask_id(Integer.parseInt(String.valueOf(temp.get("task_id"))));
                        runnableTask.setPara((String)temp.get("para"));
                        runnableTask.setTaskclassname((String)temp.get("taskclassname"));
                        //检查任务是否已在待执行列表中
                        if (JobsPool.getInstance().containJobs(runnableTask.getTask_id())) {
                            continue;
                        }
                        //增加到可执行列表
                        if(JobsPool.getInstance().addJob2Jobs(runnableTask,(String)temp.get("t_date")) ==Constants.FAIL){
                            continue;
                        };
                        //更改状态为等待
                        Task_List task_list = new Task_List(runnableTask.getTask_id(), Constants.TASK_WAIT);
                        task_list.setT_date((String)temp.get("t_date"));
                        scheduleCommonService.update(task_list);
                        LogUtil.SuccessLogAdd( Constants.LOG_INFO
                                , "Monitor_Thread task_id " + runnableTask.getTask_id()
                                , Monitor_Thread.class.getName()
                                , true);
                    }
                } else {
                    //检查是否存在未完成作业
                    List<String> notFinishDate=scheduleDao.querynotfinishDate();
                    //如果不存在未完成作业，则进行完成
                    if(notFinishDate==null || notFinishDate.size()==0){
                        //获取调度完成时间
                        T_param t_param=new T_param();
                        t_param.setName("finish_date");
                        t_param=scheduleCommonService.getOne(t_param);
                        //如果平台内部finish_date没有更新，则更新
                        if(!Constants.finish_date.equals(t_param.getValue())){
                            Constants.finish_date=t_param.getValue();
                        }
                        //同步finish_date
                        //如果数据库finish_date延迟，更新数据库
                        if(CommonUtil.string82date(t_param.getValue()).after(CommonUtil.string82date(Constants.finish_date))){
                            Constants.finish_date=t_param.getValue();
                        }
                        //如果平台finish_date延迟，更新平台
                        if(CommonUtil.string82date(Constants.finish_date).after(CommonUtil.string82date(t_param.getValue()))){
                            t_param.setValue(Constants.finish_date);
                            scheduleCommonService.update(t_param);
                        }

                        //如果数据库的finish_date与平台内部schedule_date相等，则说明schedule_date的当天任务完全完成
                        //不需要再执行调度任务
                        if(!Constants.schedule_date.equals(t_param.getValue()))
                        {
                            //不相等说明之前需要调度任务
                            //首先对finish_date+1
                            String newFinish_date=CommonUtil.date2string8(
                                    CommonUtil.dateChangeByDays(
                                            CommonUtil.string82date(Constants.finish_date)
                                            ,1)
                            );
                            //尝试对newFinish_date进行初始化,如果已经初始化过，说明newFinish_date的调度已经完成
                            //更新数据库和平台的finish_date
                            if(newFinish_datehasInit){
                                t_param.setValue(newFinish_date);
                                scheduleCommonService.update(t_param);
                                Constants.finish_date=newFinish_date;
                                newFinish_datehasInit=false;
                            }else{
                                //初始化newDate日期任务
                                scheduleDao.initInitTask(newFinish_date);
                                newFinish_datehasInit=true;
                            }
                        }
                    }
                    Date nowDate=new Date();
                    //日期变更
                    if(nowDate.after(CommonUtil.string82date(Constants.schedule_date))){
                        Constants.schedule_date=CommonUtil.date2string8(nowDate);
                        T_param param=new T_param();
                        param.setName("schedule_date");
                        param.setValue(Constants.schedule_date);
                        scheduleCommonService.update(param);
                    }
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
