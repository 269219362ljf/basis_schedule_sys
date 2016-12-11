package test;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lu on 2016/12/11.
 */
public class StockTest {


        public static void test() {
            URL ur = null;
            try {
                //搜狐股票行情历史接口
//          ur = new URL("http://q.stock.sohu.com/hisHq?code=cn_300228&start=20130930&end=20131231&stat=1&order=D&period=d&callback=historySearchHandler&rt=jsonp");
                //新浪股票行情历史接口
                //日期，开盘，最高，收盘，最低，未知
                ur = new URL("http://biz.finance.sina.com.cn/stock/flash_hq/kline_data.php?&rand=random(10000)&symbol=sh600000&end_date=20161211&begin_date=20161201&type=plain");
                HttpURLConnection uc = (HttpURLConnection) ur.openConnection();
                BufferedReader reader = new BufferedReader(new InputStreamReader(ur.openStream(),"GBK"));
                String line;
                while((line = reader.readLine()) != null){
                    System.out.println(line);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    public static void main(String[] args){
        test();
    }

}
