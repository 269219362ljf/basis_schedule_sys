package dao;

import model.Task;
import org.apache.ibatis.session.RowBounds;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18.
 */
@Repository
public class TaskDao {

    private static Logger logger = LogManager.getLogger(TaskDao.class.getName());

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //查询任务表所有任务信息
    public List<Task> queryTask(int page, int rows){

        return sqlSessionSchedule.selectList(Constants.MAPPER_TASK+ ".queryTask",new RowBounds((page-1)*rows, rows));
    }

    //插入任务信息，成功返回插入数据的id
    public int insertTask(Task task){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_TASK + ".insertTask", task);
            return task.getTask_id();
        }catch (DuplicateKeyException e){
            LogUtil.ErrorLogAdd(logger
                    ,Constants.getLogType()
                    ,"任务 "+task.getTask_id(),"插入","键值重复");
            return Constants.DUPLICATEKEYERROR;
        }catch (DataIntegrityViolationException e){
            LogUtil.ErrorLogAdd(logger
                    ,Constants.getLogType()
                    ,"任务 "+task.getTask_id(),"插入","违反完整性约束");
            return Constants.DATAVIOLATIONERROR;
        }
        catch (Exception e){
            LogUtil.ErrorLogAdd(logger
                    ,Constants.getLogType()
                    ,"任务 "+task.getTask_id(),"插入","其他原因");
            return Constants.UNKNOWNERROR;
        }
    }


    //删除任务信息
    @Transactional
    public int deleteTask(int[] task_id){
        int i=0;
        for(;i<task_id.length;i++){
            sqlSessionSchedule.delete(Constants.MAPPER_TASK+".deleteTask",task_id[i]);
        }
        return 0;
    }







}
