package dao;

import model.Task;
import model.Task_List;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import utils.Constants;

import java.util.List;

/**
 * Created by lu on 2016/10/16.
 */
@Repository
public class ScheduleDao {

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    public List<Task> queryTask(int page,int rows){
        return sqlSessionSchedule.selectList(Constants.MAPPER_TASK+ ".queryTask",new RowBounds((page-1)*rows, rows));
    }

    public List<Task_List> queryAbleRunningTaskList(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_TASK+ ".queryAbleRunningTaskList");
    }


    @Transactional
    public boolean initTaskList(){
        //初始化任务列表
        sqlSessionSchedule.insert(Constants.MAPPER_TASK+ ".initTaskList");
        //
        return true;
    }

}
