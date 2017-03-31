package stockSystem.stockJobs;


import basisSchedule.jobs.JobInterface;
import org.json.JSONArray;
import org.json.JSONObject;

import stockSystem.resultModel.T_stock_info;
import stockSystem.resultModel.T_stock_trainning_info;
import stockSystem.stockUtil.StockUtil;
import stockSystem.tablesDao.StockProcessDao;
import stockSystem.tablesDao.T_stock_infoDao;
import stockSystem.tablesDao.T_stock_trainning_infoDao;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static java.lang.Thread.sleep;

/**
 * 股票采集数据（交易数据采集）
 */
public class SCodesTrainingJob implements JobInterface {

    private StockUtil stockUtil=new StockUtil();
    private T_stock_infoDao t_stock_infoDao;
    private T_stock_trainning_infoDao t_stock_trainning_infoDao;
    private StockProcessDao stockProcessDao;


    public SCodesTrainingJob(){
        this.t_stock_infoDao= (T_stock_infoDao) CommonUtil.getBean(T_stock_infoDao.class);
        this.t_stock_trainning_infoDao= (T_stock_trainning_infoDao) CommonUtil.getBean(T_stock_trainning_infoDao.class);
        this.stockProcessDao= (StockProcessDao) CommonUtil.getBean(StockProcessDao.class);
    }

    public int work(JSONObject param) {
        //获取当天日期
        String today=CommonUtil.date2string8(new Date());
        //获取代码与最大交易日期键值对
        List<Map<String,String>> codedateMaps=stockProcessDao.queryCodeDateMap();
        for(Map<String,String> temp:codedateMaps){
            String begin_date=null;
            //如果原交易表不存在代码交易记录，则统一从20100101开始采集
            if(temp.keySet().contains("max_date")) {
                //如果最大交易日期不等于当天，则查找补充
                if (temp.get("max_date").equals(today)) {
                    continue;
                } else {
                    begin_date = temp.get("max_date");
                }
            }else {
                begin_date="20100101";
            }
                JSONArray resultArray=stockUtil.getTrainningFromCode(temp.get("stock_id"),begin_date,today);
                //resultArray证明获取数据失败
                if(resultArray==null){
                    continue;
                }
                //建立插入结果集
                List<T_stock_trainning_info> trainninglists=new ArrayList<T_stock_trainning_info>();
                //对插入数据进行初始化
                for(int i=0;i<resultArray.length();i++){
                    JSONObject result=resultArray.getJSONObject(i);
                    T_stock_trainning_info insertrow=new T_stock_trainning_info();
                    insertrow.setStock_id(temp.get("stock_id"));
                    insertrow.setS_date(result.getString("date").replace("-",""));
                    insertrow.setOpen_price(result.getDouble("open"));
                    insertrow.setMax_price(result.getDouble("max"));
                    insertrow.setClose_price(result.getDouble("close"));
                    insertrow.setMin_price(result.getDouble("min"));
                    trainninglists.add(insertrow);
                }
                if(trainninglists.size()>0) {
                    t_stock_trainning_infoDao.insert(trainninglists);
                }
                //文明获取，休眠100ms
                try {
                    sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        //工作完成
        LogUtil.SuccessLogAdd(
                Constants.LOG_INFO,
                "SCodesTrainingJob  work", "工作", true);
        return Constants.SUCCESS;
    }
}
