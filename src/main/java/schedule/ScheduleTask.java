package schedule;

import jobs.Job;
import service.ScheduleService;




public class ScheduleTask {

    public static Job getjob(){
        //FIFO
        if(!ScheduleService.getInstance().isEmpty()){
        Job returnjob=ScheduleService.getInstance().get(0);
        ScheduleService.getInstance().remove(0);
        return returnjob;}
        else{
            return null;
        }

    }



}
