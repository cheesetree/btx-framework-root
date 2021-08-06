package top.cheesetree.btx.common.util.xml;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.dom4j.*;

import java.util.List;

/**
 * xml工具�?
 *
 * @author sleep
 * @date 2016-09-13
 */
public class XmlUtil {
    /**
     * String
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static Document strToDocument(String xml) throws DocumentException {
        return DocumentHelper.parseText(xml);
    }

    /**
     * xml
     *
     * @param xml
     * @return
     * @throws DocumentException
     */
    public static JSONObject xmlToJSONObject(String xml)
            throws DocumentException {
        return elementToJSONObject(strToDocument(xml).getRootElement());
    }

    /**
     * org.dom4j.Element
     *
     * @param node
     * @return
     */
    public static JSONObject elementToJSONObject(Element node) {
        JSONObject result = new JSONObject();
        List<Attribute> listAttr = node.attributes();
        for (Attribute attr : listAttr) {
            result.put(attr.getName(), attr.getValue());
        }
        List<Element> listElement = node.elements();
        if (!listElement.isEmpty()) {
            for (Element e : listElement) {
                if (e.attributes().isEmpty() && e.elements().isEmpty()) {
                    result.put(e.getName(), e.getTextTrim());
                } else {
                    if (!result.containsKey(e.getName())) {
                        result.put(e.getName(), new JSONArray());
                    }
                    ((JSONArray) result.get(e.getName()))
                            .add(elementToJSONObject(e));
                }
            }
        }
        return result;
    }
}