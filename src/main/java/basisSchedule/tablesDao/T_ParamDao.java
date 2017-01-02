package basisSchedule.tablesDao;

import basisSchedule.resultModel.T_param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Repository
public class T_ParamDao {

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //查询参数表内所有参数
    public List<T_param> query(){
        return sqlSessionSchedule.selectList(Constants.MAPPER_TASK + ".queryT_param");
    }

    //插入参数
    public int insert(T_param t_param) {
        try {
            sqlSessionSchedule.insert(Constants.MAPPER_T_PARAM + ".insertT_param", t_param);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "参数 " + t_param.getName(), "插入", true);
            return Constants.SUCCESS;
        } catch (DuplicateKeyException e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "参数 " + t_param.getName(), "插入", "键值重复", true);
            return Constants.DUPLICATEKEYERROR;
        } catch (DataIntegrityViolationException e) {
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "参数 " + t_param.getName(), "插入", "违反完整性约束", true);
            return Constants.DATAVIOLATIONERROR;
        } catch (Exception e) {
            //e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "参数 " + t_param.getName(), "插入", "其他原因", true);
            return Constants.UNKNOWNERROR;
        }
    }

    //删除任务信息
    public Map<String,Integer> delete(List<String> param_names) {
        Map<String,Integer> resultMap=new HashMap<String, Integer>();
        for (String name : param_names) {
            try {
                sqlSessionSchedule.delete(Constants.MAPPER_T_PARAM + ".deleteT_param", name);
                resultMap.put(name,Constants.SUCCESS);
                LogUtil.SuccessLogAdd(
                        Constants.LOG_INFO,
                        "参数 " + name, "删除",true);
            } catch (Exception e) {
                resultMap.put(name,Constants.FAIL);
                LogUtil.ErrorLogAdd(
                        Constants.LOG_ERROR,
                        "参数 " + name, "删除", "未知原因",true);
            }
        }
        return resultMap;
    }


}
