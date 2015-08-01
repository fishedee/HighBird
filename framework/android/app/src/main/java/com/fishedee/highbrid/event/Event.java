package com.fishedee.highbrid.event;

import android.content.Context;
import android.webkit.JavascriptInterface;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Method;
import java.util.ArrayList;
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

    private Object createActionByName(String name)throws Exception{
        Method[] methods = this.getClass().getMethods();
        String methodName = "get"+name.substring(0,1).toUpperCase()+name.substring(1);
        Method method = null;
        for( int i = 0 ; i != methods.length ; ++i )
            if( methods[i].getName().equals(methodName)){
                method = methods[i];
                break;
            }
        if( method == null )
            throw new Exception("不存在的action:"+name);
        return method.invoke(this);
    }

    private Method createActionMethodByName(Object object,String name)throws Exception{
        Method[] methods = object.getClass().getMethods();
        Method method = null;
        for( int i = 0 ; i != methods.length ; ++i )
            if( methods[i].getName().equals(name) ){
                method = methods[i];
                break;
            }
        if( method == null )
            throw new Exception("不存在的action方法:"+object.getClass().getName()+","+name);
        return method;
    }

    private Object createActionArgvByName(Method method,int index,String name)throws Exception{
        Class[] methodArgvs = method.getParameterTypes();
        if( methodArgvs.length < index )
            throw new Exception("参数过多"+method.getName());
        Class methodArgv = methodArgvs[index];
        if( methodArgv == int.class ){
            return Integer.valueOf(name);
        }else if( methodArgv == String.class ){
            return name;
        }else{
            throw new Exception("不合法的函数参数类型"+methodArgv.getName());
        }
    }

    private void actionObjectAction(Object object,Method method,Object[] argv )throws Exception{
        //method.in
        method.invoke(object,argv);
    }


    public void action(String eventName){
        try {
            Element actionElement = m_mapEvent.get(eventName);
            if (actionElement == null)
                throw new Exception("不存在的action" + eventName);
            NodeList nodeList = actionElement.getChildNodes();
            for (int i = 0; i != nodeList.getLength(); ++i) {
                Node node = nodeList.item(i);
                if (node.getNodeType() != Node.ELEMENT_NODE)
                    continue;
                //创建执行对象
                Element element = (Element) node;
                Object actionObject = createActionByName(element.getTagName());

                //创建执行方法
                if (element.hasAttribute("method") == false)
                    throw new Exception(element.getTagName() + "缺少method属性");
                Method method = createActionMethodByName(actionObject, element.getAttribute("method"));

                //创建执行参数
                ArrayList<Object> argvs = new ArrayList<Object>();
                NodeList childNode = element.getChildNodes();
                int parameterIndex = 0;
                for (int j = 0; j != childNode.getLength(); ++j) {
                    Node childSingleNode = childNode.item(j);
                    if (childSingleNode.getNodeType() != Node.ELEMENT_NODE)
                        continue;
                    Object argv = createActionArgvByName(method, parameterIndex++, childSingleNode.getTextContent());
                    argvs.add(argv);
                }

                //执行
                actionObjectAction(actionObject, method, argvs.toArray());
            }
        }catch(Exception e){
            e.printStackTrace();
        }
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
