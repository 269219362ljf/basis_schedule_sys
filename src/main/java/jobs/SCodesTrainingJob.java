package jobs;

import dao.T_stock_infoDao;
import dao.T_stock_trainning_infoDao;
import org.json.JSONArray;
import org.json.JSONObject;
import stock.StockUtil;
import stock.T_stock_info;
import stock.T_stock_trainning_info;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/12/13.
 */
public class SCodesTrainingJob implements JobInterface {

    private StockUtil stockUtil=new StockUtil();
    private T_stock_infoDao t_stock_infoDao;
    private T_stock_trainning_infoDao t_stock_trainning_infoDao;

    public SCodesTrainingJob(){
        t_stock_infoDao= (T_stock_infoDao) CommonUtil.getBean(T_stock_infoDao.class);
        t_stock_trainning_infoDao= (T_stock_trainning_infoDao) CommonUtil.getBean(T_stock_trainning_infoDao.class);

    }

    @Override
    public int work(JSONObject param) {
        List<T_stock_info> codes=t_stock_infoDao.selectAll();
        if(codes==null){
            LogUtil.ErrorLogAdd(
                    Constants.LOG_ERROR
                    , "T_stock_infoDao  selectAll", "获取", " 未知错误 ", true);
            return Constants.FAIL;
        }
        System.out.println(param.get("begin_date"));
        String begin_date=param.has("begin_date")?
                ""+param.get("begin_date"):CommonUtil.date2string8(new Date());
        String end_date=param.has("end_date")?
                ""+param.get("end_date"):CommonUtil.date2string8(new Date());;


        for(T_stock_info code:codes){
            JSONArray resultArray=stockUtil.getTrainningFromCode(code.getStock_id(),begin_date,end_date);
            List<T_stock_trainning_info> trainninglists=new ArrayList<T_stock_trainning_info>();
            for(int i=0;i<resultArray.length();i++){
                JSONObject result=resultArray.getJSONObject(i);
                T_stock_trainning_info temp=new T_stock_trainning_info();
                temp.setStock_id(code.getStock_id());
                temp.setS_date(result.getString("date").replace("-",""));
                temp.setOpen_price(result.getDouble("open"));
                temp.setMax_price(result.getDouble("max"));
                temp.setClose_price(result.getDouble("close"));
                temp.setMin_price(result.getDouble("min"));
                trainninglists.add(temp);
            }
            if(trainninglists.size()>0) {
                t_stock_trainning_infoDao.insert(trainninglists);
            }
        }
        LogUtil.SuccessLogAdd(
                Constants.LOG_INFO,
                "SCodesTrainingJob  work", "工作", true);
        return Constants.SUCCESS;
    }
}
