package common.service;


import java.io.Serializable;
import java.util.List;

/**
 * Created by lu on 2017/7/24.
 */
public interface CommonService {

    /**
     *
     * 增加一个实体
     * @param pojo
     * @return 影响的行数 0失败，1成功
     */
    public <T extends Serializable> int save(T pojo);

    /**
     *
     * 删除实体
     *
     * @param pojo
     * @return
     */
    public <T extends Serializable> int update(T pojo);

    /**
     *
     * 更新实体
     *
     * @param pojo
     * @return
     */
    public <T extends Serializable> int delete(T pojo);

    /**
     *
     * 通过主键获取实体
     *
     * @param pojo
     * @return
     */
    public <T extends Serializable> List<T> get(T pojo);

    /**
     *
     * 查询所有实体
     *
     * @param clazz
     * @return
     */
    public <T extends Serializable> List<T> listAll(Class<T> clazz);

}
