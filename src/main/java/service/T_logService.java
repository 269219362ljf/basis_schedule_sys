package service;

import dao.T_LogDao;
import model.T_Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
@Service
public class T_logService {
    @Autowired
    private T_LogDao t_logDao;

    /**
     *查询所有任务
     */
    public List<T_Log> queryTask(int page, int rows){
        return t_logDao.query(page,rows);
    }

    /**
     *插入任务
     */
    public int insertTask(T_Log t_log){
        return t_logDao.insert(t_log);
    }

}
