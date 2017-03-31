package stockSystem.resultModel;

import java.io.Serializable;

/**
 * Created by lu on 2017/2/4.
 */
public class T_stock_sys_param implements Serializable {

    private String param_name;
    private String param_value;


    public String getParam_value() {
        return param_value;
    }

    public void setParam_value(String param_value) {
        this.param_value = param_value;
    }

    public String getParam_name() {
        return param_name;
    }

    public void setParam_name(String param_name) {
        this.param_name = param_name;
    }
}
