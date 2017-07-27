package basisSchedule.threadService;



import basisSchedule.jobs.Job;
import basisSchedule.jobs.JobInterface;
import basisSchedule.resultModel.Task_List;
import common.service.ScheduleCommonService;
import org.json.JSONObject;

import utils.ClassUtil;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;

//任务执行线程，负责调用具体任务类的work函数
public class JobThread implements Runnable {

    private Job job;
    private JSONObject param=null;

    private ScheduleCommonService scheduleCommonService;

    public JobThread (Job job){
        this.job=job;
        initJobThread();
    }

    public JobThread(Job job, JSONObject param){
        this.job=job;
        this.param=param;
    }

    public void run() {
        //默认状态为错误
        int st=Constants.TASK_FAIL;
        Task_List task_list=new Task_List();
        task_list.setTask_id(job.getTask_id());
        try{
            Class jobclass;
            //加载任务类型，如果未加载，则到自定义类路径下寻找，若仍寻找失败，则抛出错误
            try {
                jobclass = Class.forName(job.getJobClass());
            }catch (ClassNotFoundException e){
                String classname=job.getJobClass().substring(job.getJobClass().lastIndexOf(".")+1);
                jobclass=ClassUtil.getInstance().getClass(classname);
            }
            //初始化任务参数，param为固定扩展参数
            if(param == null){
                param=job.getParam();
            }else{
                param=job.getParam().append("EXParam",param);
            }
            //执行任务
            JobInterface work=(JobInterface)jobclass.newInstance();
            //获取结果
            //int result=work.work(param);
            int result= ClassUtil.getInstance().invokemethod(jobclass,"work",param);
            //结束时间
            Date end=new Date();
            //更新任务状态
            if(result== Constants.SUCCESS){
                st=Constants.TASK_SUCCESS;
            }
            //执行结束，更新状态
            task_list.setT_date(job.getJob_date());
            task_list=scheduleCommonService.getOne(task_list);
            task_list.setSt(st);
            task_list.setEnd_time(end);
            scheduleCommonService.update(task_list);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "JobThread task_id "+job.getTask_id(),"执行",true);
            //此处为添加任务完成后，对任务执行情况分析和后续处理（未实现）
            ThreadAnalyze.analyze();
        } catch (Exception e) {
            e.printStackTrace();
            //执行错误，更新状态
            task_list.setSt(st);
            scheduleCommonService.update(task_list);
            //增加错误日志
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "JobThread task_id "+job.getTask_id(),"执行",e.getClass().getName(),true);
        }
    }

    private void initJobThread(){
        scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
    }






}
