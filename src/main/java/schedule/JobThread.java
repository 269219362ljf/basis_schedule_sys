package schedule;

import dao.ScheduleDao;
import jobs.Job;
import jobs.JobInterface;
import model.Task_List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.concurrent.RecursiveAction;


public class JobThread extends RecursiveAction {

    private Job job;

    private static Logger logger = LogManager.getLogger(JobThread.class.getName());

    private ScheduleDao scheduleDao;

    public JobThread (Job job){
        this.job=job;
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String scheduleDaoBean=(context.getBeanNamesForType(ScheduleDao.class))[0];
        this.scheduleDao= (ScheduleDao) context.getBean(scheduleDaoBean);
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
            //更新数据库任务列表状态
            Task_List task_list=new Task_List(job.getTask_id(),job.getTask_st());
            scheduleDao.updateTaskListByTask_List(task_list);
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
