package service;

import dao.T_ParamDao;
import dao.TaskDao;
import dao.Task_ListDao;
import model.T_param;
import model.Task;
import model.Task_List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by lu on 2016/11/20.
 */
@Service
public class DaoService {

    @Autowired
    private TaskDao taskDao;
    @Autowired
    private T_ParamDao t_paramDao;
    @Autowired
    private Task_ListDao task_listDao;

    /**
     *查询所有任务
     */
    public List<Task> queryTask(int page, int rows){
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

    /**
     *查询任务列表
     */
    public List<Task_List> queryTask_List(){
        return task_listDao.query();
    }

    /**
     *插入任务列表
     */
    public int insertTask_List(Task_List task_list){
        return task_listDao.insert(task_list);
    }


}
