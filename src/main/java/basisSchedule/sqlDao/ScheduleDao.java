package basisSchedule.sqlDao;

import basisSchedule.resultModel.Task;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;


@Repository
public class ScheduleDao {

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //查询可执行任务信息
   public List<Task> queryRunnableList(){
       return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".queryRunnableList");
   }


    //初始化任务列表
    public int initTaskList(){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_Schedule + ".initTask_List");
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 initTaskList ", "执行",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
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
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 queryErrorCount ", "执行",true);
            return result;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "方法 queryErrorCount ", "执行", "未知原因",true);
            return result;
        }
    }

    //查询数据库是否已经初始化
    public int queryIsInit(){
        int result=Constants.FAIL;
        try {
            result=sqlSessionSchedule.selectOne(Constants.MAPPER_Schedule + ".queryIsInit");
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 queryIsInit ", "执行",true);
            return result;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(
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
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 updateAllTaskList ", "执行",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "方法 updateAllTaskList ", "执行", "未知原因",true);
            return result;
        }
    }

}
