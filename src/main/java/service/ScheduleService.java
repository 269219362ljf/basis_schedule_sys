package service;


import dao.TaskDao;
import dao.Task_ListDao;
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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;



@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private Task_ListDao task_listDao;

    //可执行任务列表
    private static List<Job> jobs=new ArrayList<Job>();
    //可执行任务列表的id
    private static Set<Integer> jobids=new HashSet<Integer>();

    public static Set<Integer> getJobids(){
        return jobids;
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
            //避免无参数任务在转换参数时产生错误
            if(param==null){
                param="{}";
            }
            //经过检查，添加到任务执行列表
            Job job=new Job(task_id,new JSONObject(param),jobClassName,Constants.TASK_READY);
            //获取信号量后进行插入操作
            try {
                ScheduleService.semaphore.acquire();
                jobs.add(job);
                jobids.add(job.getTask_id());
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

    //检查任务是否存在可执行任务列表
    public static boolean checkIsInRunnableList(int task_id){
        return jobids.contains(task_id);
    }

    //可执行任务列表中移除任务
    public static int removeJobsByTask_id(int task_id){
        //如果不存在该任务ID就返回失败
        if(!checkIsInRunnableList(task_id)){
            return Constants.FAIL;
        }
        Job job=null;
        //存在则从jobs中去除
        for(Job tempjob:jobs){
            if(tempjob.getTask_id()==task_id){
                job=tempjob;
            }
        }
        if(job!=null){
            jobs.remove(job);
            jobids.remove(task_id);
        }
        return Constants.SUCCESS;
    }

    //待执行列表是否为空
    public static boolean checkJobsEmpty(){
        return jobs.isEmpty();
    }

    //待执行列表清空
    public static void clearJobs(){
        jobs.clear();
        jobids.clear();
    }

    //获取待执行列表中的任务（根据task_id）
    public static Job getJob(int task_id){
        if(checkIsInRunnableList(task_id)){
            for(Job job:jobs){
                if(job.getTask_id()==task_id){
                    return job;
                }
            }
        }
        return null;
    }

    //查询所有任务
    public List<Task> queryAllTask(){
        return taskDao.query();
    }

    //查询任务执行状态
    public List<Task_List> queryAllTask_List(){
        return task_listDao.query();
    }




}
