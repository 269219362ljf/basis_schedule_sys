package basisSchedule.jobs;

import basisSchedule.resultModel.T_param;
import basisSchedule.scheduleService.ScheduleService;
import basisSchedule.sqlDao.ScheduleDao;
import common.service.ScheduleCommonService;
import org.json.JSONObject;
import utils.CommonUtil;
import utils.Constants;

import java.util.Date;

import static java.lang.Thread.sleep;

/**
 * 用于对作业任务进行初始化
 */
public class InitJob implements JobInterface {

    private ScheduleDao scheduleDao;

    private ScheduleCommonService scheduleCommonService;

    @Override
    public int work(JSONObject param) {
        System.out.println("InitJob begin");
        scheduleDao=(ScheduleDao)CommonUtil.getBean(ScheduleDao.class);
        scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
        Date finish_date = CommonUtil.string82date(Constants.finish_date);
        Date schedule_date = CommonUtil.string82date(Constants.schedule_date);
        //如果完成日期比调度日期的前一天还要前，则存在重跑
        if(CommonUtil.dateChangeByDays(schedule_date, -1).after(finish_date)){
            String newDate=CommonUtil.date2string8(CommonUtil.dateChangeByDays(finish_date,1));
            scheduleDao.initRepairTask_List(newDate);
        }else{
            scheduleDao.initTaskList(Constants.schedule_date);
        }
        return Constants.SUCCESS;
    }
}
