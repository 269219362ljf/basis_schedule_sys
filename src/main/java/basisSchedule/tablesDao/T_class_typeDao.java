package basisSchedule.tablesDao;

import basisSchedule.resultModel.T_class_type;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by lu on 2017/6/13.
 */
@Repository
public class T_class_typeDao {

    @Autowired()
    @Qualifier( "schedulesqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    public List<T_class_type> queryT_class_type(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_T_CLASS_TYPE+".queryT_class_typeByModel");
    }

    //插入任务类型
    public int insert(T_class_type t_class_type){
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_T_CLASS_TYPE + ".insertT_class_type", t_class_type);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "日志 " + t_class_type.getTaskclassname(), "插入",false);
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "日志 " + t_class_type.getTaskclassname(), "插入", "未知错误",false);
            return Constants.FAIL;
        }
    }


}
