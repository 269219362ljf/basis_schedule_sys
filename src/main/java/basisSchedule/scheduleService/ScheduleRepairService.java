package basisSchedule.scheduleService;

import basisSchedule.resultModel.Task;
import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;

import java.util.List;

/**
 * Created by lu on 2017/3/31.
 */
@Service
public class ScheduleRepairService {

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScheduleDao scheduleDao;

    public int checkNotFinish(){
        int able2run=-1;
        //检查之前任务是否存在错误,若存在则初始化为未执行
        able2run=scheduleDao.queryBeforeErrorCount();
        if(able2run>0 || able2run<0){
            List<String> errorDates=scheduleDao.querynotfinishDate();
            //将错误日期的任务初始化
            for(String date:errorDates){
                scheduleDao.initTaskList(date);
            }
            return repairErrorTask();
        }
        return Constants.SUCCESS;
    }


    //修复之前的错误任务，并重跑
    private int repairErrorTask(){
        if(scheduleDao.updateAllTaskListBefore()==Constants.FAIL){
            return Constants.FAIL;
        }
        return Constants.SUCCESS;
    }
}
