package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.T_LogDao;
import jobs.SCodesInitJob;
import jobs.SCodesTrainingJob;
import jobs.TestJob;
import model.Task;
import model.Task_List;
import model.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.DaoService;

import service.TestService;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private DaoService daoService;

    @Autowired
    private T_LogDao t_logDao;

    static Logger logger= LogManager.getLogger(TestController.class.getName());

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
            if(1==1) {
                Task task = new Task();
                task.setTask_id(0);
                task.setName("task" + 0);
                task.setPara("{a:0,b:0}");
                task.setTaskclassname(TestJob.class.getName());
                daoService.insertTask(task);
            }
            if(1==1) {
                Task task = new Task();
                task.setTask_id(1);
                task.setName("stockInit");
                task.setPara("{a:0,b:0}");
                task.setTaskclassname(SCodesInitJob.class.getName());
                daoService.insertTask(task);
            }

            if(1==1) {
                Task task = new Task();
                task.setTask_id(2);
                task.setName("scodestraining");
                task.setPara("{begin_date:20000101,end_date:20161216}");
                task.setTaskclassname(SCodesTrainingJob.class.getName());
                daoService.insertTask(task);
            }

            List<Task_List> task_lists=daoService.queryTask_List();
            for(Task_List task_list:task_lists){
                System.out.println("Task_list : "+task_list.getTask_id());
            }
            request.setAttribute("test3status","success");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }



}
