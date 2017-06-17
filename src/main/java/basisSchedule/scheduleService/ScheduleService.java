package basisSchedule.scheduleService;


import basisSchedule.resultModel.Dep;
import basisSchedule.resultModel.T_class_type;
import basisSchedule.tablesDao.DepDao;
import basisSchedule.tablesDao.T_class_typeDao;
import basisSchedule.tablesDao.TaskDao;
import basisSchedule.tablesDao.Task_ListDao;
import basisSchedule.resultModel.Task;
import basisSchedule.resultModel.Task_List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.ClassUtil;
import utils.CommonUtil;
import utils.Constants;
import utils.ReflectUtil;


import java.io.File;
import java.util.List;


@Service
public class ScheduleService {

    @Autowired
    private TaskDao taskDao;

    @Autowired
    private Task_ListDao task_listDao;

    @Autowired
    private DepDao depDao;

    @Autowired
    private T_class_typeDao t_class_typeDao;

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

    public List<T_class_type> queryT_class_type(){
        return t_class_typeDao.queryT_class_type();
    }

    public int insertT_class_type(String classname){
        T_class_type newclass=new T_class_type();
        newclass.setTaskclassname(classname);
        return t_class_typeDao.insert(newclass);
    }


    public int classFileUpload(MultipartFile file){
        try{
            //检查常量中，上传文件夹是否正常
            if(!CommonUtil.checkDir(Constants.UPLOADDIR)){
                return Constants.FAIL;
            }
            //将临时文件转为正式文件
            File newFile=new File(Constants.UPLOADDIR+file.getOriginalFilename());
            file.transferTo(newFile);
            //转后若文件不存在，则返回错误
            if(!newFile.exists()){
                return Constants.FAIL;
            }
            //尝试加载，并获取类名
            //Class jobclass= ClassUtil.getInstance().getClass(file.getName());
            //将类名写入数据库
            //return insertT_class_type(jobclass.getName());
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return Constants.FAIL;
        }
    }







}
