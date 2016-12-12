package schedule;

import jobs.Job;
import service.ScheduleService;




public class ScheduleTask {

    public static Job getjob(){
        //FIFO
        if(!ScheduleService.checkJobsEmpty()){
        int task_id=(int)ScheduleService.getJobids().toArray()[0];
        Job returnjob=ScheduleService.getJob(task_id);
            return returnjob;}
        else{
            return null;
        }

    }



}
