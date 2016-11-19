package dao;

import model.T_Log;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/19.
 */
@Repository
public class T_LogDao {
    private static Logger logger = LogManager.getLogger(TaskDao.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //查询日志表所有日志信息
    public List<T_Log> query(int page, int rows) {
        return sqlSessionSchedule.selectList(Constants.MAPPER_T_LOG + ".queryT_Log", new RowBounds((page - 1) * rows, rows));
    }

    //插入日志
    public int insert(T_Log t_log){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_T_LOG + ".insertT_Log", t_log);
            LogUtil.SuccessLogAdd(logger,
                    Constants.LOG_INFO,
                    "日志 " + t_log.getLog_msg(), "插入",false);
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(logger
                    , Constants.LOG_ERROR
                    , "日志 " + t_log.getLog_msg(), "插入", "未知错误",false);
            return Constants.FAIL;
        }
    }

    //删除日志
//删除任务信息
    public Map<Integer,Integer> delete(List<Integer> task_id) {
        Map<Integer,Integer> resultMap=new HashMap<Integer, Integer>();
        for (int i : task_id) {
            try {
                sqlSessionSchedule.delete(Constants.MAPPER_T_LOG + ".deleteT_Log", i);
                resultMap.put(i,Constants.SUCCESS);
                LogUtil.SuccessLogAdd(logger,
                        Constants.LOG_INFO,
                        "任务" + i+"的日志", "删除",false);
            } catch (Exception e) {
                resultMap.put(i,Constants.FAIL);
                LogUtil.ErrorLogAdd(logger,
                        Constants.LOG_ERROR,
                        "任务" + i+"的日志", "删除", "未知原因",false);
            }
        }
        return resultMap;
    }


}
