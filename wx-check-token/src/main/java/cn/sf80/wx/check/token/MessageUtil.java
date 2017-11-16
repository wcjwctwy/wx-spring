package cn.sf80.wx.check.token;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.thoughtworks.xstream.XStream;

public class MessageUtil {  

    /** 
     * 将xml消息转换成map 
     *  
     * @param req 
     *            HttpServletRequest对象 
     * @return 返回一个map 
     * @throws Exception  
     */  
    @SuppressWarnings("unchecked")
	public static Map<String, String> xmlToMap(HttpServletRequest req) throws Exception {  
        SAXReader reader = new SAXReader();  
        Map<String, String> map = new HashMap<String, String>();  

        InputStream is =  req.getInputStream();  
        Document doc =  reader.read(is);  

        // 获取根元素  
        Element root = doc.getRootElement();  
        List<Element> list = root.elements();  
        // 遍历元素并放到map里  
        for (Element e : list) {  
            map.put(e.getName(), e.getText());  
        }  
        is.close();  
        return map;  
    }  

    public static String textMessageToXml(TextMessage textMsg) {  
        XStream xstream = new XStream();  
        // 将根元素替换成<xml>，默认根元素为<类名>  
        xstream.alias("xml", textMsg.getClass());  
        String xml = xstream.toXML(textMsg);
        System.out.println("返回的消: "+xml);
        return xml;  
    }  
}  