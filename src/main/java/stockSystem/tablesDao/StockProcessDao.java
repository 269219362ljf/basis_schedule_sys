package stockSystem.tablesDao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;


import java.util.List;
import java.util.Map;


/**
 * Created by lu on 2017/3/22.
 */
@Repository
public class StockProcessDao {

    @Autowired()
    @Qualifier( "stocksqlSessionTemplate" )
    private SqlSessionTemplate sqlSessionSchedule;

    public List<Map<String,String>> queryCodeDateMap(){
        try{
            return sqlSessionSchedule.selectList(Constants.MAPPER_StockProcess+".queryCodeDateMap");
        }catch (Exception e){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "StockProcessDao  queryCodeDateMap", "获取", e.getCause().toString(), true);
            return null;
        }
    }




}
