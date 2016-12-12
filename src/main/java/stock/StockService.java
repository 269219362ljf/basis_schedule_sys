package stock;

import dao.T_stock_infoDao;
import model.T_stock_info;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import utils.Constants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/12.
 */
@Service
public class StockService {

    @Autowired
    private T_stock_infoDao t_stock_infoDao;

    private StockUtil stockUtil=new StockUtil();






}
