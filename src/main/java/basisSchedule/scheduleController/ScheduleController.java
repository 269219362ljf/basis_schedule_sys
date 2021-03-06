package basisSchedule.scheduleController;

import basisSchedule.resultModel.Dep;
import basisSchedule.resultModel.T_class_type;
import basisSchedule.resultModel.Task;
import basisSchedule.resultModel.Task_List;

import basisSchedule.scheduleService.ScheduleRepairService;
import common.service.ScheduleCommonService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import basisSchedule.scheduleService.ScheduleInitSerivce;
import basisSchedule.scheduleService.ScheduleService;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import utils.CommonUtil;
import utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


@Controller
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ScheduleInitSerivce scheduleInitSerivce;

    @Autowired
    private ScheduleRepairService scheduleRepairService;

    @Autowired
    private ScheduleCommonService scheduleCommonService;

    @RequestMapping(value="/Schedule/scheduleInit.do")
    public void scheduleInit(HttpServletRequest request, HttpServletResponse response){
        try{
            JSONObject jresult=new JSONObject();
            int result=scheduleInitSerivce.init();
            if(result==Constants.FAIL){
                jresult.put("result",result);
            }else {
                jresult.put("result",scheduleInitSerivce.init());
            }
            CommonUtil.renderData(response,jresult);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ajax请求不需要返回页面，只需要得到response中的数据即可，所以方法签名为void即可
     *  @param request
     * @param response
     */
    @RequestMapping(value = "/Schedule/queryTaskList.do")
    public void queryTaskList(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Task> tasks= scheduleCommonService.listAll(Task.class);
            //返回到前端的dataTables
            list2dataTables(response,tasks);
        }catch (Exception e){
            e.printStackTrace();
            //return null;
        }
    }

    @RequestMapping(value = "/Schedule/queryRunningState.do")
    public void queryRunningState(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Task_List> task_lists=scheduleCommonService.listAll(Task_List.class);
            list2dataTables(response,task_lists);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Schedule/addTask.do")
    public void addTask(HttpServletRequest request, HttpServletResponse response){
        try{
            JSONObject requestData=CommonUtil.requestdata2JSON(request);
            JSONObject result=new JSONObject();
            if(scheduleService.insertTask(requestData)!= Constants.SUCCESS){
                result.put("msg","error");
            }else{
                result.put("msg","success");
            }
            System.out.println(requestData);
            CommonUtil.renderData(response,result);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Schedule/queryTaskid.do")
    public void queryTaskid(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Task> tasks=scheduleCommonService.listAll(Task.class);
            String ids[]=new String[tasks.size()];
            for(int i=0;i<tasks.size();i++){
                ids[i]=tasks.get(i).getTask_id()+"";
            }
            JSONObject result=new JSONObject();
            result.put("data",ids);
            CommonUtil.renderData(response,result);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Schedule/queryDepState.do")
    public void queryDepState(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Dep> deps=scheduleCommonService.listAll(Dep.class);
            list2dataTables(response,deps);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/Schedule/queryTaskclassname.do")
    public void queryTaskType(HttpServletRequest request, HttpServletResponse response){
            try{
                List<T_class_type> classes=scheduleCommonService.listAll(T_class_type.class);
                String sclass[]=new String[classes.size()];
                for(int i=0;i<classes.size();i++){
                    sclass[i]=classes.get(i).getTaskclassname()+"";
                }
                JSONObject result=new JSONObject();
                result.put("data",sclass);
                CommonUtil.renderData(response,result);
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    @RequestMapping(value = "/Schedule/classFileUpload.do")
    public void classFileUpload(HttpServletRequest request, HttpServletResponse response, @RequestParam("classpath")MultipartFile file){
        JSONObject result=new JSONObject();
        if(scheduleService.classFileUpload(file)==Constants.SUCCESS){
            result.put("result","success");
        }else{
            result.put("result","fail");
        }
        CommonUtil.renderData(response,result);
    }

    //将list数据集返回到前端dataTables
    private void list2dataTables(HttpServletResponse response,List list){
        //所有未初始化的都会忽略，所以如果不初始化就会导致列缺失，前台报错
        JSONArray jsonArray=CommonUtil.list2JSONResult(list);
        JSONObject result=new JSONObject();
        try{
            result.put("length",jsonArray.length());
            result.put("data",jsonArray);}
        catch (NullPointerException e){
            result.put("length",0);
            result.put("data","{}");
        }
        CommonUtil.renderData(response,result);
    }



}
