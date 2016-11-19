package service;

import dao.ScheduleDao;
import dao.TaskDao;
import model.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lu on 2016/10/15.
 */

@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;



    /**
     *查询所有任务
     */
    public List<Task> queryTask(int page,int rows){
        return taskDao.query(page,rows);
    }

    /**
     *插入任务
     */
    public int insertTask(Task task){
        return taskDao.insert(task);
    }












}
