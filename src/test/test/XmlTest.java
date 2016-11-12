package test;

import org.json.JSONObject;
import utils.XmlUtil;

import java.io.File;

/**
 * Created by lu on 2016/11/13.
 */
public class XmlTest {

    public static void main(String[] args) {
        XmlUtil util=new XmlUtil();
        XmlTest test=new XmlTest();
        String xmlFile="test.xml";
        File directory = new File("..");
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





    }
    //获取当前类的路径（编译后）
    public String getPath(){
        return this.getClass().getResource("").getPath();
    }

}
