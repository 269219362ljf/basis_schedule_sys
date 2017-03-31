package tianchiPredictCustom.tianchiPredictController;

import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import tianchiPredictCustom.tianchiPredictDao.Dbconnection;
import utils.CommonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by lu on 2017/2/20.
 */
@Controller
public class TianchiPredictController {

    @RequestMapping(value="/tianchiPredictCustomTest.do")
    public void scheduleInit(HttpServletRequest request, HttpServletResponse response){
        try{
            Dbconnection con=new Dbconnection();
            JSONObject jresult=new JSONObject();
            jresult.put("result",con.findAll());
            System.out.println(jresult);
            CommonUtil.renderData(response,jresult);
        } catch (Exception e){
            e.printStackTrace();
        }
    }




}
