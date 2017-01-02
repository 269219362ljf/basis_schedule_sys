package basisSchedule.jobs;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/21.
 */
public class Job {

    private int task_id;
    private JSONObject param;
    private String jobClass;
    private int task_st;
    private int task_type;

    public Job(int task_id, JSONObject param,String jobClass,int task_st,int task_type){
        this.task_id=task_id;
        this.param=param;
        this.jobClass=jobClass;
        this.task_st=task_st;
        this.task_type=task_type;
    }


    public int getTask_id() {
        return task_id;
    }

    public void setTask_id(int task_id) {
        this.task_id = task_id;
    }

    public JSONObject getParam() {
        return param;
    }

    public void setParam(JSONObject param) {
        this.param = param;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    public int getTask_st() {
        return task_st;
    }

    public void setTask_st(int task_st) {
        this.task_st = task_st;
    }

    public int getTask_type() {
        return task_type;
    }

    public void setTask_type(int task_type) {
        this.task_type = task_type;
    }
}
