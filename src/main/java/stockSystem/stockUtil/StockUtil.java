package stockSystem.stockUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Constants;
import utils.LogUtil;
import utils.StockConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Thread.sleep;

/**
 * Created by Administrator on 2016/12/12.
 */
public class StockUtil {

    //  返回的值是一个js代码段  包括指定url页面包含的所有股票代码(新浪用)
    private String getBatchStackCodesFromSinaUrl(URL url) {
        try {
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(30000);
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = null;
            StringBuffer sb = new StringBuffer();
            boolean flag = false;
            while ((line = br.readLine()) != null) {
                if (line.contains("<script type=\"text/javascript\">") || flag) {
                    sb.append(line);
                    flag = true;
                }
                if (line.contains("</script>")) {
                    flag = false;
                    if (sb.length() > 0) {
                        if (sb.toString().contains("code_list") && sb.toString().contains("element_list")) {
                            break;
                        } else {
                            sb.setLength(0);
                        }
                    }
                }
            }
            if (br != null) {
                br.close();
                br = null;
            }
            return sb.toString();

        } catch (IOException e) {
            LogUtil.ErrorLogAdd(Constants.LOG_ERROR, "getBatchStackCodesFromSinaUrl ", "获取股票代码", e.getCause().toString(), true);
            return null;
        }

    }

    // 解析一组股票代码字符串   把code中包括的所有股票代码放入List中(新浪用)
    private List<String> handleStockCodeFromSina(String code) {
        try {
            List<String> codes = null;
            int end = code.indexOf(";");
            code = code.substring(0, end);
            int start = code.lastIndexOf("=");
            code = code.substring(start);
            code = code.substring(code.indexOf("\"") + 1, code.lastIndexOf("\""));
            String[] codesplit = code.split(",");
            codes = new ArrayList<String>();
            for (String temp : codesplit) {
                //去除非代码数据
                if (temp.length() == 8) {
                    codes.add(temp);
                }
            }
            return codes;
        }catch (Exception e){
            e.printStackTrace();
            //增加错误日志
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR,
                    "handleStockCodeFromSina","执行",e.getClass().getName(),true);
            return null;
        }
    }

    //从新浪的URL获取所有股票代码
    public List<String> getBatchStackCodesFromSina() {
        List<String> codes = new ArrayList<String>();
        try {
            for (int i = 0; i < StockConstants.SINAPAGES; i++) {
                String codesString = getBatchStackCodesFromSinaUrl(
                        new URL(StockConstants.BATCHSTACKCODESURL + i)
                );
                codes.addAll(handleStockCodeFromSina(codesString));
                //文明获取
                sleep(100);
            }
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "getBatchStackCodesFromSina", "执行", true);
            return codes;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(Constants.LOG_ERROR, "getBatchStackCodesFromSina ", "获取股票代码", e.getClass().getName(), true);
            return codes;
        }

    }

    //从新浪的URL获取股票价格（按天）
    public JSONArray getTrainningFromCode(String code, String begin_date, String end_date) {
        try {
            JSONArray results=new JSONArray();
            String urlString = StockConstants.TRAINNINGURL;
            urlString += "&symbol=" + code;
            urlString += "&end_date=" + end_date;
            urlString += "&begin_date=" + begin_date;
            urlString += "&type=plain";
            URL url = new URL(urlString);
            HttpURLConnection uc = (HttpURLConnection) url.openConnection();
            uc.setConnectTimeout(6*1000);
            if (uc.getResponseCode() != 200)
                throw new RuntimeException("请求url失败");
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), "GBK"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitlines=line.split(",");
                if(splitlines.length<6){
                    continue;
                }
                JSONObject result=new JSONObject();
                result.put("code",code);
                result.put("date",splitlines[0]);
                result.put("open",splitlines[1]);
                result.put("max",splitlines[2]);
                result.put("close",splitlines[3]);
                result.put("min",splitlines[4]);
                results.put(result);
            }
            reader.close();
            uc.disconnect();
            LogUtil.SuccessLogAdd(
                    Constants.LOG_INFO,
                    "getTrainningFromCode", "执行", true);
            return results;
        } catch (Exception e) {
            LogUtil.ErrorLogAdd(Constants.LOG_ERROR, "getTrainningFromCode ", "获取股票价格", e.getClass().toString(), true);
            return null;
        }

    }
}