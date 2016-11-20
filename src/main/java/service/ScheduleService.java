package service;

import dao.ScheduleDao;
import dao.T_ParamDao;
import dao.TaskDao;
import model.T_param;
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
    @Autowired
    private T_ParamDao t_paramDao;


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

    /**
     *查询所有参数设置
     */
    public List<T_param> queryT_param(){
        return t_paramDao.query();
    }

    /**
     *插入参数
     */
    public int insertT_param(T_param t_param){
        return t_paramDao.insert(t_param);
    }










}
