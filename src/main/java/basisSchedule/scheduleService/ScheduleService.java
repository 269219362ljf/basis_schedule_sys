package basisSchedule.scheduleService;


import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import basisSchedule.resultModel.Task;
import basisSchedule.resultModel.Task_List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;


@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private Task_ListDao task_listDao;

    //查询所有任务
    public List<Task> queryAllTask(){
        return taskDao.query();
    }

    //查询任务执行状态
    public List<Task_List> queryAllTask_List(){
        return task_listDao.query();
    }




}
