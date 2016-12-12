package jobs;

import dao.T_stock_infoDao;
import model.T_stock_info;
import org.json.JSONObject;
import stock.StockUtil;
import utils.CommonUtil;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
public class SCodesInitJob implements JobInterface {

    private StockUtil stockUtil=new StockUtil();
    private T_stock_infoDao t_stock_infoDao;

    public SCodesInitJob(){
        t_stock_infoDao= (T_stock_infoDao)CommonUtil.getBean(T_stock_infoDao.class);
    }


    public int work(JSONObject param) {
        return initStockCodes();
    }

    //初始化股票代码
    public int initStockCodes(){
        List<T_stock_info> stockarray=new ArrayList<T_stock_info>();
        List<String> codes=new ArrayList<String>();
        codes=stockUtil.getBatchStackCodesFromSina();
        if(codes.size()==0){
            return Constants.FAIL;
        }
        for(String code:codes){
            T_stock_info stock=new T_stock_info();
            stock.setStock_id(code);
            stockarray.add(stock);
        }
        if(stockarray.size()==0){
            return Constants.FAIL;
        }
        t_stock_infoDao.insert(stockarray);
        return Constants.SUCCESS;
    }

}
