package basisSchedule.threadService;

import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.tablesDao.Task_ListDao;
import basisSchedule.jobs.Job;
import basisSchedule.jobs.JobInterface;
import basisSchedule.resultModel.Task_List;
import org.json.JSONObject;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;


public class JobThread implements Runnable {

    private Job job;
    private JSONObject param=null;

    private Task_ListDao task_listDao;

    public JobThread (Job job){
        this.job=job;
        initJobThread();
    }

    public JobThread(Job job, JSONObject param){
        this.job=job;
        this.param=param;
    }

    public void run() {
        try{
            //记录开始时间
            Date begin=new Date();
            String date_string= CommonUtil.date2string8(begin);
            //执行任务
            Class jobclass=Class.forName(job.getJobClass());
            JobInterface work=(JobInterface)jobclass.newInstance();
            //更新数据库任务列表状态
            Task_List task_list=new Task_List(job.getTask_id(),Constants.TASK_RUNNING);
            task_list.setT_date(date_string);
            task_list.setBeg_time(begin);
            task_listDao.updateTaskListByTask_List(task_list);
            //初始化任务参数
            if(param == null){
                param=job.getParam();
            }else{
                param=job.getParam().append("EXParam",param);
            }
            //获取结果
            int result=work.work(param);
            //结束时间
            Date end=new Date();
            //更新任务状态
            int st=Constants.TASK_FAIL;
            if(result== Constants.SUCCESS){
                st=Constants.TASK_SUCCESS;
            }
            //执行结束，更新状态
            task_list.setSt(st);
            task_list.setEnd_time(end);
            task_listDao.updateTaskListByTask_List(task_list);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "JobThread task_id "+job.getTask_id(),"执行",true);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "JobThread task_id "+job.getTask_id(),"执行",e.getClass().getName(),true);
        }
    }

    private void initJobThread(){
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String task_listDaoBean=(context.getBeanNamesForType(Task_ListDao.class))[0];
        this.task_listDao= (Task_ListDao) context.getBean(task_listDaoBean);
    }






}
