package com.fishedee.highbrid.event;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 7/31/15.
 */
public class Event {
    public static Map<String,Element> m_mapEvent;
    public static void initialize( Element root )throws Exception{
        //初始化
        m_mapEvent = new HashMap<String,Element>();
        NodeList nodeList = root.getChildNodes();
        for( int i = 0 ; i != nodeList.getLength() ; ++i ){
            Node node = nodeList.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element)node;
            if( element.hasAttribute("name") == false )
                throw new Exception("event缺少name属性");
            String name = element.getAttribute("name");
            m_mapEvent.put(name,element);
        }
    }

    Console m_console;
    Navigation m_navigation;
    public Event(Context context){
        m_console = new Console(context);
        m_navigation = new Navigation(context);
    }

    public void action(String eventName){

    }

    @JavascriptInterface
    public Object getConsole(){
        return m_console;
    }

    @JavascriptInterface
    public Object getNavigation(){
        return m_navigation;
    }
}
