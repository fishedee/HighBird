package com.fishedee.highbrid;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.fishedee.highbrid.event.Event;
import com.fishedee.highbrid.router.Router;
import com.fishedee.highbrid.view.ViewFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.ByteArrayInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MainActivity extends Activity {
    static String m_name;
    static String m_domain;
    static String m_indexUrl;
    static String m_loadingUrl;
    static String m_loadingHtml;

    private static String getRealUrl(String url){
        if( url == null || url.equals(""))
            url = m_indexUrl;
        if( url.indexOf("http://") != 0 )
            url = "http://"+m_domain+"/"+m_indexUrl;
        return url;
    }
    public static void initialize(Context context,String url)throws Exception{
        //读取xml
        RequestQueue queue = Util.createNetworkQueue(context);
        String xml = Util.getNetworkText(queue,url);
        //解析xml
        ByteArrayInputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setIgnoringElementContentWhitespace(true);
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        //读取根元素
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getChildNodes();
        for( int i = 0 ; i != nodeList.getLength() ; ++i ){
            Node node = (Node)nodeList.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element)node;
            String name = element.getNodeName();
            String value = element.getTextContent();
            if( name.equals("name")){
                m_name = value;
            }else if( name.equals("domain")){
                m_domain = value;
            }else if( name.equals("index")){
                m_indexUrl = value;
            }else if( name.equals("loading")){
                m_loadingUrl = value;
            }else if( name.equals("router")){
                Router.initialize( element );
            }else if( name.equals("event")){
                Event.initialize(element);
            }else if( name.equals("view")){
                ViewFactory.initialize(element);
            }else{
                throw new Exception("未知属性"+name);
            }
        }
        //预读入数据
        m_loadingHtml = Util.getNetworkText(queue,getRealUrl(m_loadingUrl));
    }

    Event m_event;
    Router m_router;
    View m_view;
    String m_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try{
            setUrl();
            m_router = new Router(this);
            m_event = new Event(this);
            String viewName = m_router.getView();
            m_view = new ViewFactory(this).createView(viewName);
            setContentView(m_view);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getUrl(){
        return m_url;
    }

    private void setUrl(){
        Intent intent = new Intent();
        String url = intent.getStringExtra("url");
        m_url = getRealUrl(url);
    }

    public String getName(){
        return m_name;
    }

    public String getLoadingHtml(){
        return m_loadingHtml;
    }

    public Event getEvent(){
        return m_event;
    }

    public Router getRouter(){
        return m_router;
    }

    public View getView(){
        return m_view;
    }

    public void pushActivity(String url){
        Intent intent = new Intent();
        intent.putExtra("url", url);
        intent.setClass(this, MainActivity.class);
        this.startActivity(intent);
    }

    public void popActivity(){
        this.finish();
    }
}
