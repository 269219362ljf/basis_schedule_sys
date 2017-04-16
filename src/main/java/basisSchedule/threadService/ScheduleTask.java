package basisSchedule.threadService;

import basisSchedule.jobs.Job;
import basisSchedule.scheduleService.ScheduleService;


public class ScheduleTask {

    public static Job getjob(){
        //随机
        if(!JobsPool.getInstance().checkJobsEmpty()){
        Job returnjob=JobsPool.getInstance().getJob();
            return returnjob;}
        else{
            return null;
        }

    }



}
