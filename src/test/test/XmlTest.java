package test;



import utils.XmlConfig;


/**
 * Created by lu on 2016/11/13.
 */
public class XmlTest {

    public static void main(String[] args) {


        XmlConfig xmlConfig=XmlConfig.getInstance();
        System.out.println(xmlConfig.xmlPath);


    }

    //获取当前类的路径（编译后）
    public String getPath(){
        return this.getClass().getResource("").getPath();
    }

}
