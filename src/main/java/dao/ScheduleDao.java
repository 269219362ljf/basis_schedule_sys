package dao;

import model.RunnableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by lu on 2016/10/16.
 */
@Repository
public class ScheduleDao {

    private static Logger logger = LogManager.getLogger(ScheduleDao.class.getName());
    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //查询可执行任务信息
   public List<RunnableList> queryRunnableList(){
       return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".queryRunnableList");
   }


    //初始化任务列表
    public int initTaskList(){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_Schedule + ".initTask_List");
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "方法 initTaskList ", "执行",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "方法 initTaskList ", "执行", "未知原因",true);
            return Constants.FAIL;
        }
    }

    //获取过往任务存在错误数
    public int queryBeforeErrorCount(){
        int result=Constants.FAIL;
        try {
            result=sqlSessionSchedule.selectOne(Constants.MAPPER_Schedule + ".queryBeforeErrorCount");
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "方法 queryErrorCount ", "执行",true);
            return result;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "方法 queryErrorCount ", "执行", "未知原因",true);
            return result;
        }
    }

    //获取过往任务存在错误数
    public int queryIsInit(){
        int result=Constants.FAIL;
        try {
            result=sqlSessionSchedule.selectOne(Constants.MAPPER_Schedule + ".queryIsInit");
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "方法 queryIsInit ", "执行",true);
            return result;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "方法 queryIsInit ", "执行", "未知原因",true);
            return result;
        }
    }

    //更新任务列表任务状态（全表变更）
    public int updateAllTaskList(){
        int result=Constants.FAIL;
        try {
            result=sqlSessionSchedule.update(Constants.MAPPER_Schedule + ".updateAllTaskList");
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "方法 updateAllTaskList ", "执行",true);
            return result;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(logger,
                    Constants.LOG_ERROR,
                    "方法 updateAllTaskList ", "执行", "未知原因",true);
            return result;
        }
    }

}
