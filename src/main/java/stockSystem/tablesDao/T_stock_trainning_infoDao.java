package stockSystem.tablesDao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import stockSystem.resultModel.T_stock_trainning_info;
import utils.Constants;
import utils.LogUtil;

import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
@Repository
public class T_stock_trainning_infoDao {

    @Autowired
    private SqlSessionTemplate sqlSessionSchedule;

    //股票交易信息插入
    public int insert(List<T_stock_trainning_info> lists){
        try{
            sqlSessionSchedule.insert(Constants.MAPPER_STOCK + ".t_stock_trainning_info_insert", lists);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "T_stock_trainning_infoDao  insert", "插入", true);
            return Constants.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "T_stock_trainning_infoDao  insert", "插入", e.getCause().toString(), true);
            return Constants.FAIL;
        }
    }





}
