package controller;

import jobs.SCodesInitJob;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/12/12.
 */
@Controller
public class StockController {

    //股票代码初始化，由于不是进场初始化，所以独立出来一个指令而非一个任务
    @RequestMapping(value="/stockInit.do")
    public void stockInit(HttpServletRequest request, HttpServletResponse response){
        try{
            SCodesInitJob scjob=new SCodesInitJob();
            scjob.work(null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
