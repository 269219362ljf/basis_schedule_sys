package basisSchedule.tablesDao;

import basisSchedule.resultModel.Dep;
import basisSchedule.resultModel.Task;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by lu on 2017/3/13.
 */
@Repository
public class DepDao {

    @Autowired()
    @Qualifier( "schedulesqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    //插入任务信息，成功返回插入数据的id
    public int insert(Dep dep) {
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_Dep + ".insertDep", dep);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "依赖关系 " + dep.getTask_id(), "插入",true);
            return Constants.SUCCESS;
        } catch (DuplicateKeyException e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "依赖关系 " + dep.getTask_id(), "插入", "键值重复",true);
            return Constants.DUPLICATEKEYERROR;
        } catch (DataIntegrityViolationException e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "依赖关系 " + dep.getTask_id(), "插入", "违反完整性约束",true);
            return Constants.DATAVIOLATIONERROR;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "依赖关系 " + dep.getTask_id(), "插入", "其他原因",true);
            return Constants.UNKNOWNERROR;
        }
    }

    //查询所有依赖关系
    public List<Dep>  queryDep(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_Dep+".queryDep");
    }



}
