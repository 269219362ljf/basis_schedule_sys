package stockSystem.stockJobs;


import basisSchedule.jobs.JobInterface;
import common.service.StockCommonService;
import org.json.JSONObject;
import stockSystem.resultModel.T_stock_info;
import stockSystem.stockUtil.StockUtil;
import utils.CommonUtil;
import utils.Constants;
import utils.LogUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 股票采集数据（股票代码更新）
 */
public class SCodesInitJob implements JobInterface {

    private StockUtil stockUtil=new StockUtil();

    private StockCommonService stockCommonService;

    public SCodesInitJob(){
        this.stockCommonService=(StockCommonService)CommonUtil.getBean(StockCommonService.class);
    }


    public int work(JSONObject param) {
        return initStockCodes();
    }

    //初始化股票代码
    public int initStockCodes(){
        //获取已有的所有股票代码
        List<T_stock_info> oldarray=stockCommonService.listAll(T_stock_info.class);
        Set<String> oldstockcodes=new HashSet<String>();
        for(T_stock_info temp:oldarray){
            oldstockcodes.add(temp.getStock_id());
        }
        //获取网上所有股票代码
        List<String> codes=stockUtil.getBatchStackCodesFromSina();
        //如果未能获取，错误退出
        if(codes.size()==0){
            return Constants.FAIL;
        }
        //新股票代码
        List<T_stock_info> newarray=new ArrayList<T_stock_info>();
        for(String code:codes){
            if(!oldstockcodes.contains(code)){
                T_stock_info stock=new T_stock_info();
                stock.setStock_id(code);
                newarray.add(stock);
            }
        }
        //如果不存在新代码，则成功退出
        if(newarray.size()==0){
            return Constants.SUCCESS;
        }
        //否则插入表
        stockCommonService.inserts(newarray);
        //工作完成
        LogUtil.SuccessLogAdd(
                Constants.LOG_INFO,
                "SCodesInitJob  work", "工作", true);
        return Constants.SUCCESS;
    }

}
