package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Task;
import model.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.ScheduleService;
import service.TestService;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private ScheduleService scheduleService;

    private Logger logger = LogManager.getLogger(TestController.class.getName());

    @RequestMapping(value="/test.do")
    public void test(HttpServletRequest request,HttpServletResponse response){
        try{
            List<Test> tests=testService.selectTest(1,10,null);
            for(int i=0;i<tests.size();i++){
                logger.info(tests.get(i).getTest_id()+tests.get(i).getTest_name());
            }
            response.sendRedirect("/jsp/Test.jsp");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @RequestMapping(value="/test3.do")
    public void test3(HttpServletRequest request,HttpServletResponse response){
        try{
            List<Task> tests=scheduleService.queryTask(1,10);
            for(int i=0;i<tests.size();i++){
                logger.info("taskid :"+tests.get(i).getTask_id());
                System.out.println("taskid :"+tests.get(i).getTask_id());
            }
            request.setAttribute("test3status","success");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
