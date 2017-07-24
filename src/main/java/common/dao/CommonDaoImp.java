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

    @Override
    public <T extends Serializable> int save(T pojo) {
        try {
            sqlSessionTemplate.insert(MAPPERNS+pojo.getClass().getSimpleName()+ ".insert", pojo);
            return Constants.SUCCESS;
        } catch (DuplicateKeyException e) {
            return Constants.DUPLICATEKEYERROR;
        } catch (DataIntegrityViolationException e) {
            return Constants.DATAVIOLATIONERROR;
        } catch (Exception e) {
            return Constants.UNKNOWNERROR;
        }
    }

    @Override
    public <T extends Serializable> int update(T pojo) {
        try {
            sqlSessionTemplate.update(MAPPERNS+pojo.getClass().getSimpleName()+ ".update", pojo);
            return Constants.SUCCESS;
        } catch (Exception e) {
            return Constants.FAIL;
        }
    }

    @Override
    public <T extends Serializable> int delete(T pojo) {
        try {
            sqlSessionTemplate.delete(MAPPERNS+pojo.getClass().getSimpleName()+ ".delete", pojo);
            return Constants.SUCCESS;
        } catch (Exception e) {
            return Constants.FAIL;
        }
    }

    @Override
    public <T extends Serializable> List<T> get(T pojo) {
        try {
            List<T> result=sqlSessionTemplate.selectList(MAPPERNS+pojo.getClass().getSimpleName()+ ".select", pojo);
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public <T extends Serializable> List<T> listAll(Class<T> clazz) {
        try {
            List<T> result=sqlSessionTemplate.selectList(MAPPERNS+clazz.getSimpleName()+ ".selectAll");
            return result;
        } catch (Exception e) {
            return null;
        }
    }

}
