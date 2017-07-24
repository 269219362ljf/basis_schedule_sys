package common.service;

import common.dao.StockDaoImp;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
public class StockCommonService implements CommonService {

    @Autowired
    private StockDaoImp stockDaoImp;

    @Override
    public <T extends Serializable> int save(T pojo) {
        return stockDaoImp.save(pojo);
    }

    @Override
    public <T extends Serializable> int update(T pojo) {
        return stockDaoImp.update(pojo);
    }

    @Override
    public <T extends Serializable> int delete(T pojo) {
        return stockDaoImp.delete(pojo);
    }

    @Override
    public <T extends Serializable> List<T> get(T pojo) {
        return stockDaoImp.get(pojo);
    }

    @Override
    public <T extends Serializable> List<T> listAll(Class<T> clazz) {
        return stockDaoImp.listAll(clazz);
    }

    public <T extends Serializable> int inserts(List<T> pojos){
        return stockDaoImp.inserts(pojos);
    }




}
