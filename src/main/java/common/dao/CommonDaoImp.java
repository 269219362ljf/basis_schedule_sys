package common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import utils.Constants;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
public class CommonDaoImp implements CommonDao {

    protected SqlSessionTemplate sqlSessionTemplate;
    protected String MAPPERNS;
    protected String MAPPERSUF;

    @Override
    public <T extends Serializable> int save(T pojo) {
        try {
            sqlSessionTemplate.insert(MAPPERNS+pojo.getClass().getSimpleName()+MAPPERSUF+"insert", pojo);
            return Constants.SUCCESS;
        } catch (DuplicateKeyException e) {
            return Constants.DUPLICATEKEYERROR;
        } catch (DataIntegrityViolationException e) {
            return Constants.DATAVIOLATIONERROR;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.UNKNOWNERROR;
        }
    }

    @Override
    public <T extends Serializable> int update(T pojo) {
        try {
            sqlSessionTemplate.update(MAPPERNS+pojo.getClass().getSimpleName()+MAPPERSUF+ "update", pojo);
            return Constants.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return Constants.FAIL;
        }
    }

    @Override
    public <T extends Serializable> int delete(T pojo) {
        try {
            sqlSessionTemplate.delete(MAPPERNS+pojo.getClass().getSimpleName()+MAPPERSUF+ "delete", pojo);
            return Constants.SUCCESS;
        } catch (Exception e) {
            return Constants.FAIL;
        }
    }

    @Override
    public <T extends Serializable> List<T> get(T pojo) {
        try {
            return sqlSessionTemplate.selectList(MAPPERNS+pojo.getClass().getSimpleName()+MAPPERSUF+ "select", pojo);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T extends Serializable> T getOne(T pojo) {
        try {
            return sqlSessionTemplate.selectOne(MAPPERNS+pojo.getClass().getSimpleName()+MAPPERSUF+ "select", pojo);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public <T extends Serializable> List<T> listAll(Class<T> clazz) {
        try {
            return sqlSessionTemplate.selectList(MAPPERNS+clazz.getSimpleName()+MAPPERSUF+ "selectAll");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
