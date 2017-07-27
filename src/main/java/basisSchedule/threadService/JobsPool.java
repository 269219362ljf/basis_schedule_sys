package basisSchedule.threadService;

import basisSchedule.jobs.Job;

import basisSchedule.resultModel.Task;
import org.json.JSONObject;
import utils.Constants;
import utils.LogUtil;
import utils.ScheduleUtil;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 * 用于保存当前作业列表和作业号码清单，以及一切对作业列表的修改
 */
public class JobsPool {

    private  static  JobsPool jobsPool=new JobsPool();

    public static JobsPool getInstance(){
        return jobsPool;
    }

    //可执行任务列表
    private Set<Job> jobs=new HashSet<Job>();


    //添加任务到当前执行任务列表
    public int addJob2Jobs(Task runTask, String rundate){
        try {
            //校验任务
            int result= ScheduleUtil.checkJob(runTask.getTask_id(),runTask.getPara(),runTask.getTaskclassname());
            if(result==Constants.FAIL){
                return result;
            }
            //避免无参数任务在转换参数时产生错误
            if(runTask.getPara()==null){
                runTask.setPara("{}");
            }
            //经过检查，添加到任务执行列表
            Job job=new Job(runTask.getTask_id(),new JSONObject(runTask.getPara()),runTask.getTaskclassname(),rundate);
            //获取信号量后进行插入操作
            synchronized (jobs){
                    jobs.add(job);
            }
            LogUtil.SuccessLogAdd(Constants.LOG_INFO,
                    "任务"+runTask.getTask_id(),
                    "job2jobs",true);
            return Constants.SUCCESS;

        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类"+runTask.getTaskclassname(),"job2jobs","未知错误",true);
            return Constants.FAIL;
        }
    }

    //将任务从执行任务列表中去除
    public  int removeJob(int task_id){
        try{

                Iterator<Job> it=jobs.iterator();
                //迭代查找,找到就删除
                while(it.hasNext()){
                    Job temp=it.next();
                    if(temp.getTask_id()==task_id){
                        //当找到后，再获取锁，再删除
                        synchronized (jobs) {
                            it.remove();
                        }
                        LogUtil.SuccessLogAdd(Constants.LOG_INFO,
                                "任务"+task_id,
                                "removeJob",true);
                        return Constants.SUCCESS;
                    }
                }
            return Constants.FAIL;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类 任务编号："+task_id,"removeJob","未知错误",true);
            return Constants.FAIL;
        }
    }

    //待执行列表是否包括该任务
    public boolean containJobs(int task_id){
        Iterator<Job> it=jobs.iterator();
        while(it.hasNext()){
            if(it.next().getTask_id()==task_id){
                return true;
            }
        }
        return false;
    }


    //待执行列表是否为空
    public boolean checkJobsEmpty(){
        return jobs.isEmpty();
    }

    //待执行列表清空
    public int clearJobs(){
        try {
            synchronized (jobs) {
                jobs.clear();
            }
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
    }

    //获取待执行列表中的任务（根据task_id）
    public Job getJob(int task_id){
        Iterator<Job> it=jobs.iterator();
        while(it.hasNext()){
            Job target=it.next();
            if(target.getTask_id()==task_id){
                return target;
            }
        }
        return null;
    }

    //获取待执行列表中的任务
    public Job getJob(){
        Iterator<Job> it=jobs.iterator();
        if(it.hasNext()){
            return it.next();
        }
        return null;
    }



}
