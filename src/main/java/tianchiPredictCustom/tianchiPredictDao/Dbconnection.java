package tianchiPredictCustom.tianchiPredictDao;

import org.json.JSONArray;
import utils.CDataTransform;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.*;


/**
 * Created by lu on 2017/2/20.
 */
public class Dbconnection {

    private String databaseDriver="org.postgresql.Driver";
    private String databaseUrl="jdbc:postgresql://localhost:5432/tianchi";
    private String databaseUser="lu";
    private String databasePassword="123456";
    private Connection connection=null;
    private ResultSet rs = null;
    private PreparedStatement ps = null;

    //获取并建立连接
    public Connection getConnection(){
        try{
            if(connection==null){
                Class.forName(databaseDriver); //classLoader,加载对应驱动
                connection = (Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
            }
            return connection;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    //关闭连接
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection=null;
        }
    }

    //测试样例
    public JSONArray findAll(){
        connection = getConnection();
        JSONArray rdata = new JSONArray();
        try {
            ps = connection.prepareStatement("select * from t_shop_daycnt_tmp limit 100");
            rs = ps.executeQuery();
            rdata = CDataTransform.rsToJson(rs);
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        closeConnection();
        return rdata;
    }

}
