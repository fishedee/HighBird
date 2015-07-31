package com.fishedee.highbrid.router;

import android.content.Context;

import com.fishedee.highbrid.MainActivity;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 7/31/15.
 */
public class Router {
    public static Map<String,String> m_mapRouter;
    public static void initialize( Element root )throws Exception{
        //初始化
        m_mapRouter = new HashMap<String,String>();
        NodeList nodeList = root.getChildNodes();
        for( int i = 0 ; i != nodeList.getLength() ; ++i ){
            Node node = nodeList.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element)node;
            if( element.hasAttribute("url") == false )
                throw new Exception("router缺少url属性");
            String url = element.getAttribute("url");
            if( element.hasAttribute("view") == false )
                throw new Exception("router缺少view属性");
            String view = element.getAttribute("view");
            m_mapRouter.put(url, view);
        }
    }

    Context m_context;
    public Router(Context context){
        m_context = context;
    }

    public String getView()throws Exception{
        String url = ((MainActivity)m_context).getUrl();
        for(Map.Entry<String, String> entry:m_mapRouter.entrySet()){
            String key = entry.getKey();
            String value = entry.getValue();
            if( url.indexOf(key) != -1 )
                return value;
        }
        throw new Exception("不合法的路由"+url);
    }
}
