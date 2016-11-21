package service;

import dao.ScheduleDao;
import dao.Task_ListDao;
import jobs.Job;
import jobs.JobInterface;
import model.RunnableList;
import model.Task_List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;
import utils.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * Created by lu on 2016/10/15.
 */

@Service
public class ScheduleService {

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private ScheduleDao scheduleDao;

    //可执行任务列表
    private static List<Job> jobs=new ArrayList<Job>();

    public static List<Job> getInstance(){
        return jobs;
    }
    //可执行任务列表信号量
    public final static Semaphore semaphore=new Semaphore(1);

    private static Logger logger = LogManager.getLogger(ScheduleService.class.getName());

    //添加任务到当前执行任务列表
    public int addJob2Jobs(int task_id,String param,String jobClassName){
        try {
            //转化参数为JSONOBJECT类型
            JSONObject jparam = new JSONObject(param);
            //检查jobClassName所指的类是否存在
            Class jobclass=Class.forName(jobClassName);
            Class<?>[] interfaces=jobclass.getInterfaces();
            //检查类是否实现接口
            if(interfaces.length==0 || (!isInterfaceExist(interfaces, JobInterface.class))){
                LogUtil.ErrorLogAdd(logger,
                        Constants.LOG_ERROR,
                        "任务类"+jobClassName,"job2jobs","未实现接口",true);
                return Constants.FAIL;
            }
            //经过检查，添加到任务执行列表
            Job job=new Job(task_id,jparam,jobClassName,Constants.TASK_READY);
            //获取信号量后进行插入操作
            try {
                ScheduleService.semaphore.acquire();
                jobs.add(job);
                ScheduleService.semaphore.release();
            }catch (Exception e){
                ScheduleService.semaphore.release();
                throw e;
            }
            LogUtil.SuccessLogAdd(logger,Constants.LOG_INFO,
                    "任务"+task_id,
                    "job2jobs",true);
            return Constants.SUCCESS;

        }catch (JSONException e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "任务类"+jobClassName,"job2jobs","输入参数有误",true);
            return Constants.FAIL;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "任务类"+jobClassName,"job2jobs","找不到类",true);
            return Constants.FAIL;
        } catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "任务类"+jobClassName,"job2jobs","未知错误",true);
            return Constants.FAIL;
        }
    }

    //检查类数组中是否存在目标类
    private boolean isInterfaceExist(Class<?>[] classes,Class checkClass){
        for(int i=0;i<classes.length;i++){
            if(classes[i].equals(checkClass)){
                return true;
            }
        }
        return false;
    }

    //初始化执行任务列表
    public int initJobs(){
        List<RunnableList> runnableLists=scheduleDao.queryRunnableList();
        for(RunnableList runnableList:runnableLists){
            addJob2Jobs(runnableList.getTask_id(),runnableList.getPara(),runnableList.getTaskclassname());
        }

        return 0;
    }






}
