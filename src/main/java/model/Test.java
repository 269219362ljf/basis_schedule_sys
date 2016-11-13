package model;

import java.io.Serializable;

/**
 * Created by Administrator on 0004 2016/8/4.
 */
public class Test implements Serializable {

    private Integer test_id;
    private String test_name;

    public Integer getTest_id() {
        return test_id;
    }

    public void setTest_id(int test_id) {
        this.test_id = test_id;
    }

    public String getTest_name() {
        return test_name;
    }

    public void setTest_name(String test_name) {
        this.test_name = test_name;
    }

}
