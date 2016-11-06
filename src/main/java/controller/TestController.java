package controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Test;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import service.TestService;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    private TestService testService;

    private Logger logger = LogManager.getLogger(TestController.class.getName());

    @RequestMapping(value="/test")
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



}
