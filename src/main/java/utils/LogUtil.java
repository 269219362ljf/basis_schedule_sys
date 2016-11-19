package utils;

import org.apache.logging.log4j.Logger;

/**
 * Created by Administrator on 2016/11/19.
 */
public class LogUtil {

    //增加错误日志，可根据日志级别设定记录日志
    public static void ErrorLogAdd(Logger logger,int logType,String name,String action,String cause){
        String msg=name+" "+action+" 失败 ("+cause+")";
        switch (logType){
            case Constants.LOG_DEBUG:logger.debug(msg);
            case Constants.LOG_INFO:logger.info(msg);
            case Constants.LOG_WARN:logger.warn(msg);
            case Constants.LOG_ERROR:logger.error(msg);
            case Constants.LOG_FATAL:logger.fatal(msg);break;
                default:logger.error(logger.getName()+" 未知日志级别");
        }
    }

    //增加成功日志，可根据日志级别设定记录日志
    public static void SuccessLogAdd(Logger logger,int logType ,String name,String action){
        String msg=name+" "+action+" 成功";
        switch (logType){
            case Constants.LOG_DEBUG:logger.debug(msg);
            case Constants.LOG_INFO:logger.info(msg);break;
            default:return;
        }
    }

}
