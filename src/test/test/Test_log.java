package test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Administrator on 0012 2016/7/12.
 */
public class Test_log {
    private static Logger logger = LogManager.getLogger(Test_log.class.getName());

    public static void main(String[] args) {

        logger.trace("开始程序.");
        Hello hello= new Hello();
//        for (int i = 0; i < 10000;i++){
        if (!hello.hello()) {
            logger.error("hello");
        }
//        }
        logger.trace("退出程序.");
    }

}
