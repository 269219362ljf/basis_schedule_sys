package utils;

import config.XmlConfig;
import org.json.JSONObject;

/**
 * Created by Administrator on 0004 2016/8/4.
 */
public class Constants {

    /**
     * mybatis映射XML
     */
    public final static String MAPPER_TEST= "mapperNS.Test";
    public final static String MAPPER_TASK="mapperNS.Task";
    public final static String MAPPER_T_LOG="mapperNS.T_Log";
    public final static String MAPPER_T_PARAM="mapperNS.T_param";
    public final static String MAPPER_TASK_LIST="mapperNS.Task_List";
    public final static String MAPPER_Schedule="mapperNS.Schedule";



    /*
     *系统默认值
     */
    public final static int SUCCESS=0;
    public final static int FAIL=1;

    public final static int LOG_DEBUG=0;
    public final static int LOG_INFO=1;
    public final static int LOG_WARN=2;
    public final static int LOG_ERROR=3;
    public final static int LOG_FATAL=4;

    public final static int TASK_READY=0;
    public final static int TASK_RUNNING=1;
    public final static int TASK_WAIT=2;
    public final static int TASK_SUCCESS=3;
    public final static int TASK_FAIL=4;
    public final static int TASK_PASS=5;




    private final static String PropertyFile="system-property.xml";

    /**
     *sql错误返回值
     */
    public final static int DUPLICATEKEYERROR=-1;
    public final static int DATAVIOLATIONERROR=-2;

    public final static int UNKNOWNERROR=-99;

    /*
     *系统配置
     */
    private static int LOGTYPE=-1;

    //获取当前设置日志等级
    public static int getLogType(){
        if(LOGTYPE==-1){
            initSystemProperties();
            return LOGTYPE;
        }else{
            return LOGTYPE;
        }
    }

    //根据xml文件初始化系统参数
    private static void initSystemProperties(){
        try{
            XmlConfig config=XmlConfig.getInstance();
            JSONObject xml=config.getXMLconfig(PropertyFile);
            JSONObject properties=xml.getJSONObject("properties");
            LOGTYPE=properties.get("logtype")==null?LOG_INFO:properties.getInt("logtype");
        }catch (Exception e){
            e.printStackTrace();
        }
    }




}
