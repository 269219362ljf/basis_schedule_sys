package utils;


import java.util.HashMap;

/**
 * Created by Administrator on 2017/3/2.
 */
public class DataResourceConfig extends org.apache.commons.dbcp2.BasicDataSource {

    private String propertyFile;

    public String getPropertyFile() {
        return propertyFile;
    }

    public void setPropertyFile(String propertyFile) {
        this.propertyFile = propertyFile;
    }




    public void init(){
        setBasicDataSource(propertyFile);
    }

    private void setBasicDataSource(String propertyFile){
        System.out.println(propertyFile);
        propertyFile=this.getClass().getResource("/").getPath()+propertyFile;
        HashMap<String,String> params= CommonUtil.getPropertiesData(propertyFile);
        if(params==null){
            System.out.println("scheduleDB.properties cannot get");
            return ;
        }
        setDriverClassName(params.get("jdbc.driverClassName"));
        setUrl(params.get("jdbc.url"));
        setUsername(params.get("jdbc.username"));
        setPassword(params.get("jdbc.password"));
        setInitialSize(1);
        setMaxTotal(30);
        setMaxWaitMillis(60000);
        setMaxIdle(20);//连接池最大空闲
        setMinIdle(3);//连接池最小空闲
        setRemoveAbandonedOnBorrow(true);//自动清除无用连接
        setRemoveAbandonedTimeout(180);//清除无用连接的等待时间
        setValidationQueryTimeout(1);
        setConnectionProperties("clientEncoding=UTF-8");//连接属性
    }
}
