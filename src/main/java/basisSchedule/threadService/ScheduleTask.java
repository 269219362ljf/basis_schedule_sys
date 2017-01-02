package basisSchedule.threadService;

import basisSchedule.jobs.Job;
import basisSchedule.scheduleService.ScheduleService;


public class ScheduleTask {

    public static Job getjob(){
        //FIFO
        if(!JobsPool.getInstance().checkJobsEmpty()){
        int task_id=JobsPool.getInstance().getJobids(0);
        Job returnjob=JobsPool.getInstance().getJob(task_id);
            return returnjob;}
        else{
            return null;
        }

    }



}
