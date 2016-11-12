package test;

import java.sql.*;

/**
 * 测试数据库连接
 */
public class TestConnection {

    public static void main(String[] args) {
        String jdbc_driver="org.postgresql.Driver";
        String url="jdbc:postgresql://localhost:5432/schedule_sys";
        String user="lu";
        String pass="123456";
        String sql = "select 1 as ak";
        Connection conn = null;
        Statement stmt = null;
        ResultSet  rs=null;
        try{
            Class.forName(jdbc_driver).newInstance();
            conn = DriverManager.getConnection(url, user, pass);
            stmt = conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE
                    , ResultSet.CONCUR_UPDATABLE);
            rs   = stmt.executeQuery(sql);
            rs.next();
            int rows=rs.getRow();
            if(conn!=null){
                System.out.println("connect success");
                System.out.println("rows count :"+rows);
            }else{
                System.out.println("connect fail");
            }
        }catch(Exception e){
            e.printStackTrace();;
        }finally {
            try {
                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }

    }

}
