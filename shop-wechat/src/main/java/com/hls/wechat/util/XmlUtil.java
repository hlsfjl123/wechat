package com.hls.wechat.util;

import lombok.extern.slf4j.Slf4j;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: User-XH251
 * @Date: 2022/6/24 11:27
 */
@Slf4j
public class XmlUtil {
    public static Map<String, String> xmlToMap(InputStream inputStream) {
        Map<String, String> map = new HashMap<>();
        try {
            SAXReader reader = new SAXReader();
            org.dom4j.Document document = reader.read(inputStream);
            Element root = document.getRootElement();
            List<Element> elementList = root.elements();
            // 遍历所有子节点
            for (Element e : elementList){
                map.put(e.getName(), e.getText());
            }
            // 释放资源
            inputStream.close();
        } catch (IOException | DocumentException e) {
            log.info("xml转化为map出现异常：{}", e.getMessage());
        }
        return map;
    }
}
