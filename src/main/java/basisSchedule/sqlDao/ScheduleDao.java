package basisSchedule.sqlDao;

import basisSchedule.resultModel.Task;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.Date;
import java.util.List;
import java.util.Map;


@Repository
public class ScheduleDao {

    @Autowired ()
    @Qualifier( "schedulesqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    //查询可执行任务信息
   public List<Map<String,Object>> queryRunnableList(){
       return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".queryRunnableList",null);
   }

    //查询指定可执行任务信息
    public List<Task> queryRunnableListByDateString(String dateString){
        return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".queryRunnableList", dateString);
    }

    //查询指定可执行任务信息
    public List<String> querynotfinishDate(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".querynotfinishDate");
    }


    //初始化指定日期任务列表
    public int initTaskList(String dateString){
        return insert("initTask_List",dateString);
    }

    //初始化指定日期任务列表(补跑)
    public int initRepairTask_List(String dateString){
        return insert("initRepairTask_List",dateString);
    }

    //初始化初始任务
    public int initInitTask(String dateString){
        return insert("initTask_ListBegin",dateString);
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
        return update("updateAllTaskList",null);
    }

    //更新表操作
    private int update(String sqlid,Object parameter){

        try {
            if(parameter==null) {
                sqlSessionSchedule.update(Constants.MAPPER_Schedule + "." + sqlid);
            }else{
                sqlSessionSchedule.update(Constants.MAPPER_Schedule + "." + sqlid,parameter);
            }
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 updateAllTaskList "+sqlid, "执行",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "方法 updateAllTaskList "+sqlid, "执行", "未知原因",true);
            return Constants.FAIL;
        }
    }

    //初始化任务列表
    private int insert(String sqlid,Object parameter){
        try {

            if(parameter==null) {
                sqlSessionSchedule.insert(Constants.MAPPER_Schedule + "." + sqlid);
            }else{
                sqlSessionSchedule.insert(Constants.MAPPER_Schedule + "." + sqlid,parameter);
            }
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "方法 initTaskList "+sqlid, "执行",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "方法 initTaskList "+sqlid, "执行", "未知原因",true);
            return Constants.FAIL;
        }
    }





}
