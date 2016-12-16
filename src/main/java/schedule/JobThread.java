package schedule;

import dao.ScheduleDao;
import dao.Task_ListDao;
import jobs.Job;
import jobs.JobInterface;
import model.Task_List;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import service.ScheduleService;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.concurrent.RecursiveAction;


public class JobThread extends RecursiveAction {

    private Job job;


    private ScheduleDao scheduleDao;

    private Task_ListDao task_listDao;

    public JobThread (Job job){
        this.job=job;
        WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
        String scheduleDaoBean=(context.getBeanNamesForType(ScheduleDao.class))[0];
        this.scheduleDao= (ScheduleDao) context.getBean(scheduleDaoBean);
        String task_listDaoBean=(context.getBeanNamesForType(Task_ListDao.class))[0];
        this.task_listDao= (Task_ListDao) context.getBean(task_listDaoBean);
    }

    @Override
    protected void compute() {
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
            //获取结果
            int result=work.work(job.getParam());
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
}
