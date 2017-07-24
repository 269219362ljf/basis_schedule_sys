package common.service;

import common.dao.ScheduleDaoImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
@Service
public class ScheduleCommonService implements CommonService {

    @Autowired
    private ScheduleDaoImp scheduleDaoImp;


    @Override
    public <T extends Serializable> int save(T pojo) {
        return scheduleDaoImp.save(pojo);
    }

    @Override
    public <T extends Serializable> int update(T pojo) {
        return scheduleDaoImp.update(pojo);
    }

    @Override
    public <T extends Serializable> int delete(T pojo) {
        return scheduleDaoImp.delete(pojo);
    }

    @Override
    public <T extends Serializable> List<T> get(T pojo) {
        return scheduleDaoImp.get(pojo);
    }

    @Override
    public <T extends Serializable> List<T> listAll(Class<T> clazz) {
        return scheduleDaoImp.listAll(clazz);
    }
}
