package service;

import dao.TestDao;
import model.Test;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class TestService {
    @Autowired
    private TestDao testDAO;

    public List<Test> selectTest(int page, int rows, Test test) {
        return testDAO.queryTest(page,rows,test);
    }
}
