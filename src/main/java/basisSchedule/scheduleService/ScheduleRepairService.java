package basisSchedule.scheduleService;

import basisSchedule.sqlDao.ScheduleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.CommonUtil;
import utils.Constants;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by lu on 2017/3/31.
 */
@Service
public class ScheduleRepairService {

    @Autowired
    private ScheduleDao scheduleDao;

    @Autowired
    private ScheduleService scheduleService;

    private long sleeptime=1000000;

    public int repair() {
        int able2run = -1;
        //检查之前任务是否存在错误,若存在则初始化为未执行
        able2run = scheduleDao.queryBeforeErrorCount();
        Date finish_date = CommonUtil.string82date(Constants.finish_date);
        Date schedule_date = CommonUtil.string82date(Constants.schedule_date);
        String newdate = null;
        try {
            //修复之前的错误任务
            if (CommonUtil.dateChangeByDays(schedule_date, -1).after(schedule_date)) {
                scheduleDao.updateAllTaskList();
            }
            //如果日期比之前完成时间要晚
            while (CommonUtil.dateChangeByDays(schedule_date, -1).after(schedule_date)) {
                if (newdate == CommonUtil.date2string8(CommonUtil.dateChangeByDays(finish_date, 1))) {
                    Thread.sleep(sleeptime);
                } else {
                    newdate = CommonUtil.date2string8(CommonUtil.dateChangeByDays(finish_date, 1));
                    scheduleDao.initRepairTask_List(newdate);
                }
            }
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
    }
}
