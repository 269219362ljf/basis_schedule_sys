package utils;

import jobs.JobInterface;
import org.json.JSONException;
import org.json.JSONObject;


public class ScheduleUtil {

    //校验任务
    public static int checkJob(int task_id,String param,String jobClassName){
        try {
            //转化参数为JSONOBJECT类型
            JSONObject jparam = new JSONObject(param);
            //检查jobClassName所指的类是否存在
            Class jobclass = Class.forName(jobClassName);
            Class<?>[] interfaces = jobclass.getInterfaces();
            //检查类是否实现接口
            if (interfaces.length == 0 || (!isInterfaceExist(interfaces, JobInterface.class))) {
                LogUtil.ErrorLogAdd(
                        Constants.LOG_ERROR,
                        "任务类" + jobClassName, "checkJob", "未实现接口", true);
                return Constants.FAIL;
            }
            return Constants.SUCCESS;
        }catch (JSONException e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类"+jobClassName,"checkJob","输入参数有误",true);
            return Constants.FAIL;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务类" + jobClassName, "checkJob", "找不到类", true);
            return Constants.FAIL;
        }
    }

    //检查类数组中是否存在目标类
    private static boolean isInterfaceExist(Class<?>[] classes,Class checkClass){
        for (Class<?> temp:classes) {
            if(temp.equals(checkClass)){
                return true;
            }
        }
        return false;
    }



}
