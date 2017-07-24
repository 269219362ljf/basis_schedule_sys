package utils;


import basisSchedule.resultModel.T_Log;
import common.service.ScheduleCommonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Date;

/**
 * Created by Administrator on 2016/11/19.
 */
public class LogUtil {

    private static Logger logger = LogManager.getLogger(LogUtil.class.getName());

    //增加错误日志，可根据日志级别设定记录日志
    public static void ErrorLogAdd(int logType,String name,String action,String cause,boolean writeDatabase){
        String msg=name+" "+action+" 失败 ("+cause+")";
        if(logType>=Constants.getLogType()) {
            switch (logType) {
                case Constants.LOG_DEBUG:
                    logger.debug(msg);
                    break;
                case Constants.LOG_INFO:
                    logger.info(msg);
                    break;
                case Constants.LOG_WARN:
                    logger.warn(msg);
                    break;
                case Constants.LOG_ERROR:
                    logger.error(msg);
                    break;
                case Constants.LOG_FATAL:
                    logger.fatal(msg);
                    break;
                default:
                    logger.error(logger.getName() + " 未知日志级别");
            }
            if(writeDatabase) {
                insertT_log(logType, msg);
            }
        }
    }

    //增加成功日志，可根据日志级别设定记录日志
    public static void SuccessLogAdd(int logType ,String name,String action,boolean writeDatabase){
        String msg=name+" "+action+" 成功";
        if(logType>=Constants.getLogType()) {
            switch (logType) {
                case Constants.LOG_DEBUG:
                    logger.debug(msg);
                case Constants.LOG_INFO:
                    logger.info(msg);
                    break;
                default:
            }
            if(writeDatabase) {
                insertT_log(logType, msg);
            }
        }
    }

    private static void insertT_log(int log_lv,String msg){
        T_Log t_log=new T_Log();
        t_log.setLog_time(new java.sql.Date(new Date().getTime()));
        t_log.setLog_level(log_lv);
        t_log.setLog_msg(msg);
        ScheduleCommonService scheduleCommonService= (ScheduleCommonService) CommonUtil.getBean(ScheduleCommonService.class);
        scheduleCommonService.save(t_log);
    }

}
