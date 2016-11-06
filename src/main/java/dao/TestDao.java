package dao;


import model.Test;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import utils.Constants;

import java.util.List;

@Repository
public class TestDao implements Constants{

    @Autowired
    private SqlSessionTemplate sqlSessionFastCard;

    /**
     * 测试用select
     * @param page
     * @param rows
     * @param test  对应表'test'
     * @return
     */
    public List<Test> queryTest(int page, int rows, Test test) {
        return sqlSessionFastCard.selectList(Constants.MAPPER_TEST+ ".queryTest",test,new RowBounds((page-1)*rows, rows));
    }


}
