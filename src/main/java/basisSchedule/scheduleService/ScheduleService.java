package basisSchedule.scheduleService;


import basisSchedule.resultModel.Dep;
import basisSchedule.resultModel.Task;

import basisSchedule.sqlDao.ScheduleDao;
import common.service.ScheduleCommonService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import utils.CommonUtil;
import utils.Constants;


import java.io.File;


@Service
public class ScheduleService {

    @Autowired
    private ScheduleCommonService scheduleCommonService;

    @Autowired
    private ScheduleDao scheduleDao;

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
            if(scheduleCommonService.save(task)!= Constants.SUCCESS){
                return Constants.FAIL;
            }else{
                scheduleCommonService.save(dep);
            }
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return Constants.FAIL;
        }
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

    //初始化作业
    public int initTask(String date){
        return scheduleDao.initTaskList(date);
    }

    //初始化补跑作业（）
    public int initRepairTask(String date){
        return scheduleDao.initTaskList(date);
    }





}
