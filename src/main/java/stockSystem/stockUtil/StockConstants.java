package stockSystem.stockUtil;

import stockSystem.resultModel.T_stock_sys_param;
import stockSystem.tablesDao.T_stock_sys_paramDao;
import utils.CommonUtil;

import java.util.List;

/**
 * Created by lu on 2017/2/4.
 */
public class StockConstants {

    private static String lastFinishDate=null;


    public static String getLastFinishDate(){
        if(lastFinishDate==null) {
            T_stock_sys_paramDao dao = (T_stock_sys_paramDao) CommonUtil.getBean(T_stock_sys_paramDao.class);
            lastFinishDate=dao.selectAll().get("lastFinishDate");
        }
        return lastFinishDate;
    }






}
