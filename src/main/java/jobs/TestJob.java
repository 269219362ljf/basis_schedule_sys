package jobs;

import org.json.JSONObject;

/**
 * Created by Administrator on 2016/11/21.
 */
public class TestJob implements JobInterface {


    public int work(JSONObject param) {
        System.out.println("test start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("test end");
        return 0;
    }
}
