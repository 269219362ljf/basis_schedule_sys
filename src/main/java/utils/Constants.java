package utils;


import basisSchedule.resultModel.T_param;
import common.service.ScheduleCommonService;
import org.json.JSONObject;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 0004 2016/8/4.
 */
public class Constants {

    private static String separator= File.separator;

    /**
     * 常用公共变量
     */

    /**
     * mybatis映射XML
     */
    public final static String MAPPER_Schedule="mapperNS.Schedule";

    public final static String MAPPER_StockProcess="mapperNS.Stockprocess";


    /*
     *系统默认值
     */
    public final static int SUCCESS=0;
    public final static int FAIL=-1;

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

    public final static int BEFOREERRORXIST=1;

    //任务类型
    public final static int TASK_HURRY=0;//优先
    public final static int TASK_NORMAL=1;//普通
    public final static int TASK_SCHEDULE=2;//周期
    public final static int TASK_SINGLE=3;//同步


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

    public  static int SLEEPTIME=1000;
    public  static int MONITORSLEEPTIME=10000;
    public  static int THREADCOUNT=10;
    public  static String UPLOADDIR=System.getProperty("webApp.root")
            +separator+"WEB-INF"
            +separator+"upload"
            +separator;
    private static boolean initflg=true;
    public static String schedule_date=null;
    public static String finish_date=null;


    private static int LOGTYPE=-1;

    private static ScheduleCommonService scheduleCommonService;

    public static void init(){
        scheduleCommonService=(ScheduleCommonService)CommonUtil.getBean(ScheduleCommonService.class);
        if(initflg){
            initSystemProperties();
            initPropertiesFromDB();
            initflg=false;
        }
    }

    //获取当前设置日志等级
    public static int getLogType(){
        return LOGTYPE;
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

    private static void initPropertiesFromDB(){
        List<T_param> params=scheduleCommonService.listAll(T_param.class);
        HashMap<String,String> temps=new HashMap<String,String>();
        for(T_param temp:params){
            temps.put(temp.getName(),temp.getValue());
        }
        schedule_date=getProperties(temps,"schedule_date",CommonUtil.date2string8(new Date()));
        finish_date=getProperties(temps,"finish_date",CommonUtil.date2string8(new Date()));
    }

    private static String getProperties(HashMap<String,String> temps,String name,String devalue){
        if(temps.containsKey(name)){
            return temps.get(name);
        }else{
            T_param newParam=new T_param();
            newParam.setName(name);
            newParam.setValue(devalue);
            scheduleCommonService.save(newParam);
            return devalue;
        }
    }




    }

