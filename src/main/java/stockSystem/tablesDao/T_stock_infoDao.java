package stockSystem.tablesDao;

import org.springframework.beans.factory.annotation.Qualifier;
import stockSystem.resultModel.T_stock_info;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
@Repository
public class T_stock_infoDao {

    @Autowired ()
    @Qualifier( "stocksqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    //股票信息插入
    public int insert(List<T_stock_info> lists){
        try{
            sqlSessionSchedule.insert(Constants.MAPPER_STOCK + ".t_stock_info_insert", lists);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "T_stock_infoDao  insert", "插入", true);
            return Constants.SUCCESS;
        }catch (Exception e){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "T_stock_infoDao  insert", "插入", e.getCause().toString(), true);
            return Constants.FAIL;
        }
    }

    //股票代码信息获取（全）
    public List<T_stock_info> selectAll(){
        try{
            return sqlSessionSchedule.selectList(Constants.MAPPER_STOCK+".t_stock_info_selectAll");
        }catch (Exception e){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "T_stock_infoDao  selectAll", "获取", e.getCause().toString(), true);
            return null;
        }

    }

}
