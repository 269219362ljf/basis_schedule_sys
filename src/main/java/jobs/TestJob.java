package jobs;

import org.json.JSONObject;
import utils.Constants;

/**
 * Created by Administrator on 2016/11/21.
 */
public class TestJob implements JobInterface {


    public int work(JSONObject param) {
        System.out.println("job param is "+param.toString()+" begin");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("job param is "+param.toString()+" end");
        return Constants.SUCCESS;
    }
}
