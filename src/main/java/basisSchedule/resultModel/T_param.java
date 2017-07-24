package basisSchedule.resultModel;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/18.
 */
public class T_param implements Serializable {
    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
