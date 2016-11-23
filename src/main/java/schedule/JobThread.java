package schedule;

import jobs.Job;
import jobs.JobInterface;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.ScheduleService;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Administrator on 2016/11/23.
 */
public class JobThread extends RecursiveAction {

    private Job job;

    private static Logger logger = LogManager.getLogger(JobThread.class.getName());

    public JobThread (Job job){
        this.job=job;
    }

    @Override
    protected void compute() {
        try{
            //开始时间
            Date begin=new Date();
            //执行任务
            Class jobclass=Class.forName(job.getJobClass());
            JobInterface work=(JobInterface)jobclass.newInstance();
            //获取结果
            int result=work.work(job.getParam());
            //结束时间
            Date end=new Date();
            //更新任务状态
            int st=4;
            if(result== Constants.SUCCESS){
                st=3;
            }
            job.setTask_st(st);
            //任务完成删除
            List<Job> jobList= ScheduleService.getInstance();
            jobList.remove(job);
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "JobThread task_id "+job.getTask_id(),"执行",true);
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "JobThread task_id "+job.getTask_id(),"执行",e.getClass().getName(),true);
        }


    }
}
