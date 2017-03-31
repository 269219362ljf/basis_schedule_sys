package basisSchedule.scheduleService;


import basisSchedule.resultModel.Dep;
import basisSchedule.tablesDao.DepDao;
import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import basisSchedule.resultModel.Task;
import basisSchedule.resultModel.Task_List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;
import utils.ReflectUtil;


import java.util.List;


@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private DepDao depDao;

    //查询所有任务
    public List<Task> queryAllTask(){
        return taskDao.query();
    }

    //查询任务执行状态
    public List<Task_List> queryAllTask_List(){
        return task_listDao.query();
    }

    //插入任务
    public int insertTask(JSONObject input){
        Task task=new Task();
        Dep dep=new Dep();
        try{
            task.setTask_id(input.getInt("task_id"));
            task.setName(input.getString("name"));
            task.setDes(input.getString("des"));
            task.setTaskclassname(input.getString("taskclassname"));
            task.setType(input.getInt("type"));
            task.setPara(input.getString("para"));
            task.setPrior(input.getInt("prior"));
            dep.setTask_id(task.getTask_id());
            dep.setParent_id(input.getInt("parent_id"));
            if(taskDao.insert(task)!= Constants.SUCCESS){
                return Constants.FAIL;
            }else{
                depDao.insert(dep);
            }
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return Constants.FAIL;
        }
    }

    //查询所有依赖关系
    public List<Dep> queryAllDep(){
        return depDao.queryDep();
    }

}
