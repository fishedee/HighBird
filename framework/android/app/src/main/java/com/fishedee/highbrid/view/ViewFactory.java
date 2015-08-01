package com.fishedee.highbrid.view;

import android.content.Context;
import android.view.View;

import com.fishedee.highbrid.MainActivity;
import com.fishedee.highbrid.view.widget.ColorParameter;
import com.fishedee.highbrid.view.widget.DimemsionParameter;
import com.fishedee.highbrid.view.widget.IntegerParameter;
import com.fishedee.highbrid.view.widget.StringParameter;

import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by fish on 7/30/15.
 */
public class ViewFactory {
    Context m_context;
    static Map<String,Element> m_mapView;

    public static void initialize(Element root)throws Exception{
        //初始化
        m_mapView = new HashMap<String,Element>();
        NodeList nodeList = root.getChildNodes();
        for( int i = 0 ; i != nodeList.getLength() ; ++i ){
            Node node = nodeList.item(i);
            if(node.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element element = (Element)node;
            if( element.hasAttribute("name") == false )
                throw new Exception("View缺少name属性");
            String name = element.getAttribute("name");
            element.removeAttribute("name");
            m_mapView.put(name,element);
        }
    }

    public ViewFactory(Context context){
        m_context = context;
    }

    public View createView(String name)throws Exception{
        Element element = m_mapView.get(name);
        if( element == null )
            throw new Exception("没有"+name+"的View");
        return createViewFromElemnt(element);
    }

    private boolean isLayoutAttribute(String attribute){
        String[] layoutAttribute = new String[]{"float","width","height","marginTop","marginLeft","marginRight","marginBottom"};
        for( int i = 0 ; i != layoutAttribute.length ; ++i )
            if( layoutAttribute[i].toUpperCase().equals( attribute.toUpperCase() ))
                return true;
        return false;
    }

    private View createViewFromName(String name)throws Exception{
        Class elementClass = Class.forName("com.fishedee.highbrid.view." + name);
        Constructor elementConstructor = elementClass.getConstructor(Context.class);
        View elementObject = (View)elementConstructor.newInstance(m_context);
        return elementObject;
    }

    private Object createViewLayout(View view)throws Exception{
        Class layoutClass = Class.forName(view.getClass().getName()+"$MyLayout");
        Constructor layoutConstructor = layoutClass.getConstructor();
        Object layout = (Object)layoutConstructor.newInstance();
        return layout;
    }

    public static int dip2px(Context context, float dpValue){
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dpValue*scale + 0.5f);
    }

    private static int parseInt(String inString){
        String string;
        int radix = 10;

        if( inString.indexOf('#') == 0 ){
            string = inString.substring(1);
            radix = 16;
        }else if( inString.indexOf('0') == 0 ){
            string = inString.substring(1);
            radix = 8;
        }else{
            string = inString;
            radix = 10;
        }
        string = string.replaceAll("[^0-9A-Fa-f]","");
        return new BigInteger(string,radix).intValue();
    }

    private Object createViewParameter(Class parameterType,String parameterString)throws Exception{
        //执行预定义变量过滤
        if( parameterString.indexOf("@") == 0 ){
            MainActivity activity = (MainActivity)m_context;
            parameterString = activity.getSystemVariable(parameterString.substring(1));
        }
        //设置数据
        if( parameterType == IntegerParameter.class ){
            IntegerParameter result = new IntegerParameter();
            result.value = parseInt(parameterString);
            return result;
        }else if( parameterType == StringParameter.class ){
            StringParameter result = new StringParameter();
            result.value = parameterString.trim();
            return result;
        }else if( parameterType == ColorParameter.class ){
            ColorParameter result = new ColorParameter();
            result.value = parseInt(parameterString);
            return result;
        }else if( parameterType == DimemsionParameter.class ){
            DimemsionParameter result = new DimemsionParameter();
            if( parameterString.lastIndexOf("px") == parameterString.length() - 2 )
                parameterString = parameterString.substring(0,parameterString.length()-2);
            result.value = dip2px(m_context, parseInt(parameterString));
            return result;
        }else{
            throw new Exception("不合法的参数类型"+parameterType.getName());
        }
    }

    private void setObjectFromNameAndValue(Object view,String name,String value)throws Exception{
        //确定方法
        String methodName = "setMy"+name.substring(0,1).toUpperCase()+name.substring(1);
        Method[] viewMethods = view.getClass().getMethods();
        Method method = null;
        for( int i = 0 ; i != viewMethods.length ; ++i )
            if( viewMethods[i].getName().equals(methodName)  ){
                method = viewMethods[i];
                break;
            }
        if( method == null )
            throw new Exception(view.getClass().getName()+"缺少"+name+"属性设置");

        //确定参数
        Class[] parameterTypes = method.getParameterTypes();
        if( parameterTypes.length != 1 )
            throw new Exception(methodName+"只允许有一个参数");
        Class parameterType = parameterTypes[0];
        Object parameter = createViewParameter(parameterType,value);

        //设置
        method.invoke(view,parameter);
    }

    private void setViewFromNameAndValue(View view,String name,String value)throws Exception{
        setObjectFromNameAndValue(view,name,value);
    }

    private void setViewLayoutFromNameAndValue(Object layout,String name,String value)throws Exception{
        setObjectFromNameAndValue(layout, name, value);
    }

    private void addViewChildren( View view , View childView,Object childLayout)throws Exception{
        Class viewClass = view.getClass();
        Method methods[] = viewClass.getMethods();
        Method method = null;
        for( int i = 0 ; i != methods.length ; ++i )
            if( methods[i].getName().equals("addMyView")){
                method = methods[i];
                break;
            }
        if( method == null )
            throw new Exception(view.getClass().getName()+"没有实现addMyView，不能嵌入子View");
        method.invoke(view,childView,childLayout);
    }

    private View createViewFromElemnt(Element element)throws Exception{
        //根据Tag新建类
        String elementTag = element.getTagName();
        View view = createViewFromName(elementTag);

        //根据Attribute赋值属性
        NamedNodeMap attributes = element.getAttributes();
        for( int i = 0 ; i != attributes.getLength() ; ++i ){
            Attr attr = (Attr)attributes.item(i);
            if( isLayoutAttribute(attr.getName()) == true )
                continue;
            String name = attr.getName();
            String value = attr.getValue();
            setViewFromNameAndValue(view,name,value);
        }

        //生成Children并加入到本View中
        NodeList childNodeList = element.getChildNodes();
        for( int i = 0 ; i != childNodeList.getLength() ; ++i ){
            Node childNode = childNodeList.item(i);
            if(childNode.getNodeType() != Node.ELEMENT_NODE)
                continue;
            Element childElement = (Element)childNode;
            NamedNodeMap childElementAttributes = childElement.getAttributes();

            Object childLayout = createViewLayout(view);
            for( int j = 0 ; j != childElementAttributes.getLength() ; ++j ) {
                Attr attr = (Attr) childElementAttributes.item(j);
                if( isLayoutAttribute(attr.getName()) == false )
                    continue;
                String name = attr.getName();
                String value = attr.getValue();
                setViewLayoutFromNameAndValue(childLayout,name,value);
            }

            View childView = createViewFromElemnt(childElement);
            addViewChildren(view,childView,childLayout);
        }

        return view;
    }
}
