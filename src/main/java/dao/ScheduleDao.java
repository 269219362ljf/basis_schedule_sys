package dao;

import model.RunnableList;
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

    //查询可执行任务信息
   public List<RunnableList> queryRunnableList(){
       return sqlSessionSchedule.selectList(Constants.MAPPER_Schedule+".queryRunnableList");
   }


    @Transactional
    public boolean initTaskList(){
        //初始化任务列表

        //
        return true;
    }

}
