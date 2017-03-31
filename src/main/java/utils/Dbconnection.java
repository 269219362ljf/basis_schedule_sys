package utils;

import java.io.*;
import java.sql.*;
import java.util.Properties;
import java.util.concurrent.Semaphore;

/**
 * Created by lu on 2017/2/3.
 */
public class Dbconnection {

    private String databaseDriver;
    private String databaseUrl;
    private String databaseUser;
    private String databasePassword;
    private Connection connection=null;
    private static Dbconnection dbconnection=null;
    private String propertieFile;
    private static Semaphore semaphore=new Semaphore(1);

    public static Dbconnection getInstance(){
        if(dbconnection==null){
        dbconnection=new Dbconnection();}
        return dbconnection;
    }
    public Dbconnection(){
        Properties prop = new Properties();
        propertieFile=this.getClass().getResource("/").getPath()+"config/jdbc.properties";
        //读取数据库配置文件jdbc.properties
        try {
            InputStream in = new BufferedInputStream(new FileInputStream(propertieFile));
            prop.load(in);
            databaseDriver=prop.getProperty("jdbc.driverClassName");
            databaseUrl=prop.getProperty("jdbc.url");
            databaseUser=prop.getProperty("jdbc.username");
            databasePassword=prop.getProperty("jdbc.password");
            in.close();
//            String sql="SELECT task_id, st, t_date, beg_time, end_time FROM public.t_task_list";
//            PreparedStatement pstmt;
//            pstmt = (PreparedStatement)connection.prepareStatement(sql);
//            ResultSet rs = pstmt.executeQuery();
//            int col = rs.getMetaData().getColumnCount();
//            System.out.println("============================");
//            while (rs.next()) {
//                for (int i = 1; i <= col; i++) {
//                    System.out.print(rs.getString(i) + "\t");
//                    if ((i == 2) && (rs.getString(i).length() < 8)) {
//                        System.out.print("\t");
//                    }
//                }
//                System.out.println("");
//            }
//            System.out.println("============================");
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取并建立连接(获取信号量)
    public Connection getConnection(){
       try{
           semaphore.acquire();
           if(connection==null){
               Class.forName(databaseDriver); //classLoader,加载对应驱动
               connection = (Connection) DriverManager.getConnection(databaseUrl, databaseUser, databasePassword);
           }
           return connection;
       }catch (Exception e){
           semaphore.release();
       }
        return null;
    }

    //关闭连接(释放信号量)
    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            connection=null;
            semaphore.release();
        }
    }

    //sql示例
    public boolean sqlTest(){
        boolean result=false;
        try{
            Connection con=Dbconnection.getInstance().getConnection();
            String sql="create table test1 (testKey varchar(3),testValue int)";
            Statement stmt = con.createStatement();
            stmt.execute(sql);
            result=true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            closeConnection();
            return result;
        }
    }



}
