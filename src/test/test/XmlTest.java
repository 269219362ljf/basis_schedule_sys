package test;

import config.XmlConfig;
import org.json.JSONObject;
import utils.XmlUtil;

import java.io.File;
import java.util.Set;

/**
 * Created by lu on 2016/11/13.
 */
public class XmlTest {

    public static void main(String[] args) {
        XmlUtil util=new XmlUtil();
        XmlTest test=new XmlTest();
        String xmlFile="test.xml";
        String path=System.getProperty("user.dir");
        xmlFile=test.getPath()+xmlFile;
        System.out.println(xmlFile);
        System.out.println(test.getPath());
        File file=new File(xmlFile);
        if(file.exists()){
            System.out.println("success");
            System.out.println(file.getAbsolutePath());
        }else{
            System.out.println("fail");
            return;
        }
        JSONObject jsonTest=util.xml2JSONObject(file);
        System.out.println(jsonTest);

        XmlConfig config=XmlConfig.getInstance();
        jsonTest=config.getXMLconfig("paramconfig.xml");
        System.out.println("*****************");
        System.out.println(jsonTest);
        System.out.println("*****************");
        if(config.isNodeInXml("paramconfig.xml","test-value1")){
            System.out.println("*****************");
            System.out.println("node exists");
            System.out.println("*****************");
        }else{
            System.out.println("*****************");
            System.out.println("node not exists");
            System.out.println("*****************");
        }

    }

    //获取当前类的路径（编译后）
    public String getPath(){
        return this.getClass().getResource("").getPath();
    }

}
