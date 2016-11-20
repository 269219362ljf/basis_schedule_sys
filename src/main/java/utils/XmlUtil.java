package utils;

import org.json.JSONObject;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

/**
 * xml解释工具类
 */
public class XmlUtil {

    //将xml文件转为JSONObject对象
    public static JSONObject xml2JSONObject(File xmlFile) {
        // ❶Ⅰ获得DocumentBuilderFactory
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        JSONObject result=new JSONObject();
        try {
            // ❷Ⅱ获得DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();
            // ❸Ⅲ--获得文档对象--
            Document doc = builder.parse(xmlFile);
            // ❹Ⅳ获得根元素
            Element element = doc.getDocumentElement();
            // ❺Ⅴ用方法遍历递归打印根元素下面所有的ElementNode(包括TextNode非空的值)
            result.put(element.getTagName(),listAllChildNodes(element));
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 递归遍历并加入到result
     * 由于xml结点中<key>value</key>，key为一个结点，value为一个文本结点，所以key和value并不是单纯键值对
     * 而是父子结点的关系，key为父结点的name，value为子文本结点的value，因此遍历时，需要父子结点遍历
     */
    private  static JSONObject listAllChildNodes(Node node){
        NodeList nodeList=node.getChildNodes();
        JSONObject result=new JSONObject();
        //遍历子结点
        for(int i=0;i<nodeList.getLength();i++) {
            Node tempnode = nodeList.item(i);
            //如果子结点为有效结点，则进行处理
            if (tempnode.getNodeType() == Node.ELEMENT_NODE) {
                //存在子结点才进行处理
                if (tempnode.hasChildNodes()) {
                    //获取子结点的子结点列表
                    NodeList tempnodeList = tempnode.getChildNodes();
                    //遍历子结点的子结点列表
                    for (int k = 0; k < tempnodeList.getLength(); k++) {
                        //如果子结点的子结点为文本结点，且为有效字符则插入JSONObject
                        if (tempnodeList.item(k).getNodeType() == Node.TEXT_NODE
                                && (!tempnodeList.item(k).getTextContent()
                                .matches("\\s+"))) {// 用正则选取内容包含非空格的有效字符的文本节点
                            result.put(tempnode.getNodeName(), tempnodeList.item(k).getTextContent());
                            break;
                        } else if (tempnodeList.item(k).getNodeType() == Node.ELEMENT_NODE) {
                            //如果子结点的子结点为有效结点，则将设子结点类型为JSONObject并递归遍历
                            result.put(tempnode.getNodeName(), listAllChildNodes(tempnode));
                            break;
                        }
                    }
                }
            }
        }
        return result;
    }


}
