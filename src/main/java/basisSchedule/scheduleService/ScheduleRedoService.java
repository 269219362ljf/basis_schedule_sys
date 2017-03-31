package basisSchedule.scheduleService;

import basisSchedule.sqlDao.ScheduleDao;
import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by lu on 2017/3/31.
 */
@Service
public class ScheduleRedoService {

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private ScheduleDao scheduleDao;

}
