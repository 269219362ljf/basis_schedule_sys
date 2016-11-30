package dao;

import model.Task_List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;


@Repository
public class Task_ListDao {

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    /*
    查询所有列表中所有任务
     */
    public List<Task_List> query(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_TASK_LIST+".queryTask_List");
    }

    /*
    往任务列表插入任务
     */
    public int insert(Task_List task_list){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_TASK_LIST + ".insertTask_List", task_list);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "任务列表 " + task_list.getTask_id(), "插入", true);
            return Constants.SUCCESS;
        } catch (DuplicateKeyException e) {
            LogUtil.ErrorLogAdd(
                     Constants.LOG_ERROR
                    , "任务列表 " + task_list.getTask_id(), "插入", "键值重复", true);
            return Constants.DUPLICATEKEYERROR;
        } catch (DataIntegrityViolationException e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "任务列表 " + task_list.getTask_id(), "插入", "违反完整性约束", true);
            return Constants.DATAVIOLATIONERROR;
        } catch (Exception e) {
            //e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "任务列表 " + task_list.getTask_id(), "插入", "其他原因", true);
            return Constants.UNKNOWNERROR;
        }
    }

    //删除任务信息
    public int  delete(int task_id) {
        try {
            sqlSessionSchedule.delete(Constants.MAPPER_TASK_LIST + ".deleteTask_ListById", task_id);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "任务列表 task_id" + task_id, "删除",true);
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "任务列表 task_id" + task_id, "删除", "未知原因",true);
            return Constants.FAIL;
        }
    }


}
