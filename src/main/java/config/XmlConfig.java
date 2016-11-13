package config;

import org.json.JSONObject;
import utils.XmlUtil;

import java.io.File;
import java.util.Set;

/**
 * 获取xml配置文件信息，xml配置文件需要放置在resources的config文件夹下
 */
public class XmlConfig {

    private String xmlPath=null;
    private static XmlConfig xmlconfig=new XmlConfig();

    public static XmlConfig getInstance(){
        return xmlconfig;
    }

    private  XmlConfig(){
        this.xmlPath=this.getClass().getResource("").getPath();
    }

    //获取XML配置，结果为JSONObject
    public JSONObject getXMLconfig(String xmlFilename){
        String xmlFilePath=this.xmlPath+xmlFilename;
        File xmlFile=new File(xmlFilePath);
        if(!xmlFile.exists()){
            System.out.println("xmlfile not exists");
            return null;
        }else{
            return XmlUtil.xml2JSONObject(xmlFile);
        }
    }

    //检查配置是否存在于某个XML配置
    public boolean isNodeInXml(String xmlFilename,String node){
        JSONObject xml=getXMLconfig(xmlFilename);
        if(xml==null){
            return false;
        }
        return isNodeInJSON(xml,node);
    }

    //检查JSONObject里面存在对应参数
    private boolean isNodeInJSON(JSONObject data,String node){
        Set<String> keys=data.keySet();
        for(String key:keys){
            if(key.equals(node)){
                return true;
            }
            //若为JSONObject则递归遍历
            if(data.get(key).getClass()==JSONObject.class){
                return isNodeInJSON(data.getJSONObject(key),node);
            }
        }
        return false;
    }



}
