package test;

import org.json.JSONArray;
import org.json.JSONObject;
import stock.StockUtil;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    // 获取新浪38也的所有股票代码
    private static List<String> getAllStackCodes() throws IOException {
        List<String> codes = new ArrayList<String>() ;
        int i =1 ;
        URL url = null ;
        // 新浪 股票 好像目前为止就 38页
        //更新共47
        for(; i < 48 ; i ++ ){
            url = new URL("http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p="+i) ;
            String code = getBatchStackCodes(url) ;
            codes.addAll(handleStockCode(code)) ;
        }

        return codes ;
    }




    //   返回的值是一个js代码段  包括指定url页面包含的所有股票代码
    public static String getBatchStackCodes(URL url) throws IOException{
        URLConnection connection = url.openConnection() ;
        connection.setConnectTimeout(30000) ;
        BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream())) ;
        String line = null ;
        StringBuffer sb = new StringBuffer() ;
        boolean flag =false ;
        while((line = br.readLine()) != null ){
            if(line.contains("<script type=\"text/javascript\">") || flag){
                sb.append(line) ;
                flag = true ;
            }
            if(line.contains("</script>")){
                flag =false ;
                if(sb.length() > 0 ){
                    if(sb.toString().contains("code_list") && sb.toString().contains("element_list")){
                        break ;
                    }else{
                        sb.setLength(0) ;
                    }
                }
            }
        }
        if(br != null ){
            br.close() ;
            br= null ;
        }
        System.out.println(sb.toString());
        return sb.toString() ;
    }

    // 解析一组股票代码字符串   把code中包括的所有股票代码放入List中
    public static List<String> handleStockCode(String code){
        List<String> codes = null ;
        int end = code.indexOf(";") ;
        code = code.substring(0,end) ;
        int start = code.lastIndexOf("=") ;
        code = code.substring(start) ;
        code = code.substring(code.indexOf("\"")+1,code.lastIndexOf("\"")) ;
        codes = Arrays.asList(code.split(",")) ;
        return codes ;
    }



    public static void main(String[] args){
        StockUtil stockUtil=new StockUtil();
        JSONArray testResults=stockUtil.getTrainningFromCode("sh600000","20161201","20161211");
        for(int i=0;i<testResults.length();i++){
            JSONObject temp=testResults.getJSONObject(i);
            System.out.println(temp);
        }



//        try {
//            List<String> codes = new ArrayList<String>() ;
//            String batchstackcodes=StockTest.getBatchStackCodes(new URL("http://vip.stock.finance.sina.com.cn/q/go.php/vIR_CustomSearch/index.phtml?p=1"));
//            codes.addAll(StockTest.handleStockCode(batchstackcodes));
//            for(String temp:codes){
//                System.out.println(temp);
//            }
//
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

}
