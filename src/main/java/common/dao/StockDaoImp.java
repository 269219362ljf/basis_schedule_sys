package common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import utils.Constants;
import utils.LogUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
@Repository
public class StockDaoImp extends CommonDaoImp {

    @Autowired()
    public StockDaoImp(@Qualifier( "stocksqlSessionTemplate" ) SqlSessionTemplate sqlSessionStock){
        this.sqlSessionTemplate=sqlSessionStock;
        this.MAPPERNS="mapperNS.Stock.";
        this.MAPPERSUF="_";
    }

    //多条插入
    public <T extends Serializable> int inserts(List<T> pojos){
        try{
            if(pojos==null || pojos.size()==0){
                return Constants.FAIL;
            }
            sqlSessionTemplate.insert(MAPPERNS+ pojos.get(0).getClass().getSimpleName()+MAPPERSUF+".inserts", pojos);
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    pojos.get(0).getClass().getSimpleName()+" inserts", "插入", true);
            return Constants.SUCCESS;
        }catch (Exception e){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , pojos.get(0).getClass().getSimpleName()+" inserts", "插入", e.getCause().toString(), true);
            return Constants.FAIL;
        }
    }

}
