package stockSystem.tablesDao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import stockSystem.resultModel.T_stock_sys_param;
import utils.Constants;
import utils.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lu on 2017/2/4.
 */
@Repository
public class T_stock_sys_paramDao {

    @Autowired ()
    @Qualifier( "stocksqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    //股票分析系统参数获取（全）
    public Map<String,String> selectAll(){
        Map<String,String> results=null;
        try{
            List<T_stock_sys_param> lists=sqlSessionSchedule.selectList(Constants.MAPPER_STOCK+".t_stock_sys_param_selectAll");
            if(lists!=null){
                results= new HashMap<String,String>();
                for(T_stock_sys_param temp:lists){
                    results.put(temp.getParam_name(),temp.getParam_value());
                }
            }
        }catch (Exception e){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "T_stock_infoDao  selectAll", "获取", e.getCause().toString(), true);
        }
        return results;
    }
}
