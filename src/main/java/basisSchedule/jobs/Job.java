package basisSchedule.jobs;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/21.
 */
public class Job {

    private int task_id;
    private JSONObject param;
    private String jobClass;
    private String job_date;

    public Job(int task_id, JSONObject param,String jobClass,String job_date){
        this.task_id=task_id;
        this.param=param;
        this.jobClass=jobClass;
        this.job_date=job_date;
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

    public String getJob_date() {
        return job_date;
    }

    public void setJob_date(String job_date) {
        this.job_date = job_date;
    }
}
