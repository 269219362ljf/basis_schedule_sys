package basisSchedule.threadService;


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

//任务执行线程，负责调用具体任务类的work函数
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
        //记录开始时间
        Date begin=new Date();
        String date_string= CommonUtil.date2string8(begin);
        //任务记录新建
        Task_List task_list=new Task_List(job.getTask_id(),Constants.TASK_RUNNING);
        task_list.setT_date(date_string);
        task_list.setBeg_time(begin);
        //默认状态为错误
        int st=Constants.TASK_FAIL;
        try{
            Class jobclass;
            //加载任务类型，如果未加载，则到自定义类路径下寻找，若仍寻找失败，则抛出错误
            try {
                jobclass = Class.forName(job.getJobClass());
            }catch (ClassNotFoundException e){
                String classname=job.getJobClass().substring(job.getJobClass().lastIndexOf(".")+1);
                jobclass=JobsLoader.getInstance().loadClass(classname);
            }
            //执行任务
            JobInterface work=(JobInterface)jobclass.newInstance();
            //更新数据库任务列表状态
            task_listDao.updateTaskListByTask_List(task_list);
            //初始化任务参数，param为固定扩展参数
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
            //此处为添加任务完成后，对任务执行情况分析和后续处理（未实现）
            ThreadAnalyze.analyze();

        } catch (Exception e) {
            e.printStackTrace();
            //执行错误，更新状态
            task_list.setSt(st);
            task_listDao.updateTaskListByTask_List(task_list);
            //增加错误日志
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
