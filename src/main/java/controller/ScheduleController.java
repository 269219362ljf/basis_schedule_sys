package controller;

import model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.ScheduleService;

import utils.CommonUtil;
import utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by lu on 2016/10/15.
 */
@Controller
public class ScheduleController {

    static Logger logger = LogManager.getLogger(ScheduleController.class.getName());

    @Autowired
    private ScheduleService scheduleService;

    @RequestMapping(value="/scheduleInit.do")
    public void scheduleInit(HttpServletRequest request, HttpServletResponse response){
        try{
        int result= Constants.FAIL;
        result=scheduleService.init();
        switch (result){
            case Constants.BEFOREERRORXIST:request.setAttribute("result","BEFOREERRORXIST");break;
            case Constants.FAIL:request.setAttribute("result","FAIL");break;
            default:request.setAttribute("result","SUCCESS");
        }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ajax请求不需要返回页面，只需要得到response中的数据即可，所以方法签名为void即可
     *  @param request
     * @param response
     */
    @RequestMapping(value = "/queryTaskList.do")
    public void queryTaskList(HttpServletRequest request, HttpServletResponse response){
        try{
            List<Task> tasks= scheduleService.queryAllTask();
            //所有未初始化的都会忽略，所以如果不初始化就会导致列缺失，前台报错
            JSONArray jsonArray=CommonUtil.list2JSONResult(tasks);
            JSONObject result=new JSONObject();
            result.put("length",jsonArray.length());
            result.put("data",jsonArray);
            System.out.println(jsonArray);
            CommonUtil.renderData(response,result);
        }catch (Exception e){
            e.printStackTrace();
            //return null;
        }

    }





}
