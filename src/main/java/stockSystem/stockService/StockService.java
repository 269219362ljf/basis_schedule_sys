package stockSystem.stockService;

import stockSystem.stockUtil.StockUtil;
import stockSystem.tablesDao.T_stock_infoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/12/12.
 */
@Service
public class StockService {

    @Autowired
    private T_stock_infoDao t_stock_infoDao;

    private StockUtil stockUtil=new StockUtil();






}
