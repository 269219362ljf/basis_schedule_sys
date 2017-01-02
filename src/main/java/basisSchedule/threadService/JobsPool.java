package basisSchedule.threadService;

import basisSchedule.jobs.Job;

import org.json.JSONObject;
import utils.Constants;
import utils.LogUtil;
import utils.ScheduleUtil;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 用于保存当前作业列表和作业号码清单，以及一切对作业列表的修改
 */
public class JobsPool {

    private static JobsPool jobsPool=new JobsPool();

    public static JobsPool getInstance(){
        return jobsPool;
    }

    //可执行任务列表
    private Set<Job> jobs=new HashSet<Job>();
    //可执行任务列表的id
    private Set<Integer> jobids=new HashSet<Integer>();
    //可执行任务列表信号量
    private final static Semaphore semaphore=new Semaphore(1);

    //添加任务到当前执行任务列表
    public int addJob2Jobs(int task_id,String param,String jobClassName,int task_type){
        try {
            int result= Constants.FAIL;
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
            Job job=new Job(task_id,new JSONObject(param),jobClassName,Constants.TASK_READY,task_type);
            //获取信号量后进行插入操作
            try {
                semaphore.acquire();
                jobs.add(job);
                jobids.add(job.getTask_id());
                semaphore.release();
            }catch (Exception e){
                semaphore.release();
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

    //将任务从执行任务列表中去除
    public  int removeJob(int task_id){
        int result=Constants.FAIL;
        try{
            //不存在该任务编号
            if(!jobids.contains(task_id)){
                return result;
            }
            semaphore.acquire();
            Job target=null;
            for(Job job:jobs){
                if(job.getTask_id()==task_id){
                    target=job;
                    break;
                }
            }
            if(target==null){
                semaphore.release();
                return result;
            }
            jobs.remove(target);
            jobids.remove(task_id);
            semaphore.release();
            result=Constants.SUCCESS;
            LogUtil.SuccessLogAdd(Constants.LOG_INFO,
                    "任务"+task_id,
                    "removeJob",true);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类 任务编号："+task_id,"removeJob","未知错误",true);
            return result;
        }






    }

    //检查任务是否存在可执行任务列表
    public boolean checkIsInRunnableList(int task_id){
        return jobids.contains(task_id);
    }

    //待执行列表是否为空
    public boolean checkJobsEmpty(){
        return jobids.isEmpty();
    }

    //待执行列表清空
    public int clearJobs(){
        try {
            semaphore.acquire();
        jobs.clear();
        jobids.clear();
        semaphore.release();
            return Constants.SUCCESS;
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
    }

    //获取待执行列表中的任务（根据task_id）
    public Job getJob(int task_id){
        if(checkIsInRunnableList(task_id)){
            for(Job job:jobs){
                if(job.getTask_id()==task_id){
                    return job;
                }
            }
        }
        return null;
    }

    //获取
    public Integer  getJobids(int index){
        return (Integer)jobids.toArray()[index];
    }



}
