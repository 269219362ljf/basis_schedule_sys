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
        request.setAttribute("result",result);
        } catch (Exception e){
            e.printStackTrace();
        }
    }





}
