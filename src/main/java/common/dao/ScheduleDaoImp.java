package common.dao;

import basisSchedule.resultModel.Task;
import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;
import utils.CommonUtil;
import utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
@Repository
public class ScheduleDaoImp extends CommonDaoImp {

    @Autowired()
    public ScheduleDaoImp(@Qualifier( "schedulesqlSessionTemplate" )SqlSessionTemplate sqlSessionSchedule){
        this.sqlSessionTemplate=sqlSessionSchedule;
        this.MAPPERNS="mapperNS.";
    }

}
