package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ScheduleService;
import utils.Constants;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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





}
