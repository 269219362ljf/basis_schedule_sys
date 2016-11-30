package service;

import dao.ScheduleDao;
import dao.TaskDao;
import jobs.Job;
import model.Task;
import model.Task_List;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;
import utils.LogUtil;
import utils.ScheduleUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;



@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;


    //可执行任务列表
    private static List<Job> jobs=new ArrayList<Job>();

    public static List<Job> getInstance(){
        return jobs;
    }
    //可执行任务列表信号量
    private final static Semaphore semaphore=new Semaphore(1);

    //添加任务到当前执行任务列表
    public static int addJob2Jobs(int task_id,String param,String jobClassName){
        try {
            int result=Constants.FAIL;
            //校验任务
            result= ScheduleUtil.checkJob(task_id,param,jobClassName);
            if(result==Constants.FAIL){
                return result;
            }
            //经过检查，添加到任务执行列表
            Job job=new Job(task_id,new JSONObject(param),jobClassName,Constants.TASK_READY);
            //获取信号量后进行插入操作
            try {
                ScheduleService.semaphore.acquire();
                jobs.add(job);
                ScheduleService.semaphore.release();
            }catch (Exception e){
                ScheduleService.semaphore.release();
                throw e;
            }
            LogUtil.SuccessLogAdd(Constants.LOG_INFO,
                    "任务"+task_id,
                    "job2jobs",true);
            return Constants.SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类"+jobClassName,"job2jobs","未知错误",true);
            return Constants.FAIL;
        }
    }

    //查询所有任务
    public List<Task> queryAllTask(){
        return taskDao.query();
    }






}
