package com.fishedee.highbrid;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.fishedee.highbrid.data.Config;
import com.fishedee.highbrid.layout.MyWebView;

import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/**
 * Created by fish on 7/27/15.
 */
public class DashBoard {
    private static DashBoard dashBoard;
    Config m_config;
    String m_loadingPage;
    boolean m_hasInitialize;

    private DashBoard() {
        m_hasInitialize = false;
    }

    public static DashBoard getInstance() {
        if (dashBoard == null) {
            dashBoard = new DashBoard();
        }
        return dashBoard;
    }

    private String getNetworkFile(String address) throws Exception {
        //读取配置文件
        URL url = new URL(address);
        HttpURLConnection urlConn = (HttpURLConnection)url.openConnection();
        InputStream inputStream = urlConn.getInputStream();
        StringBuffer stringBuffer = new StringBuffer();
        byte[] buffer = new byte[4096];
        for (int n; (n = inputStream.read(buffer)) != -1; ) {
            stringBuffer.append(new String(buffer, 0, n,"UTF-8"));
        }
        String jsonString = stringBuffer.toString();
        return jsonString;
    }

    private Config parseConfigFile(String jsonString) throws Exception {
        //分析为json格式文件
        JSONTokener jsonTokener = new JSONTokener(jsonString);
        JSONObject jsonObject = (JSONObject) jsonTokener.nextValue();
        Config config = new Config();

        config.setTitle(jsonObject.getString("name"));
        config.setDomain(jsonObject.getString("domain"));
        config.setIndex(jsonObject.getString("index"));
        config.setLoading(jsonObject.getString("loading"));

        LinkedHashMap<String, Config.URLInfo> mapUrlInfo = new LinkedHashMap<String, Config.URLInfo>();
        JSONObject urlObject = jsonObject.getJSONObject("url");
        Iterator keyIter = urlObject.keys();
        while (keyIter.hasNext()) {
            Config.URLInfo urlInfo = new Config.URLInfo();

            String key = (String) keyIter.next();
            JSONObject value = urlObject.getJSONObject(key);
            HashMap<String, String> options = new HashMap<String, String>();
            Iterator valueIter = value.keys();

            while (valueIter.hasNext()) {
                String key2 = (String) valueIter.next();
                String value2 = value.getString(key2);
                options.put(key2, value2);
            }

            if (options.get("style") == null)
                throw new Exception(key + " 路由缺少style属性");

            urlInfo.setStyle(options.get("style"));
            options.remove("style");
            urlInfo.setOptions(options);

            mapUrlInfo.put(key, urlInfo);
        }
        config.setUrls(mapUrlInfo);

        return config;
    }

    private String getRealUrl(String address) {
        return "http://" + m_config.getDomain() + "/" + address;
    }

    private void loadConfig(String configUrl) throws Exception {
        String file = getNetworkFile(configUrl);
        m_config = parseConfigFile(file);
    }

    private void loadLoadingPage() throws Exception {
        m_loadingPage = getNetworkFile(getRealUrl(m_config.getLoading()));
    }

    public void initialize(String configUrl) {
        try {
            if (m_hasInitialize)
                return;

            //读取配置文件
            loadConfig(configUrl);

            //预读取loading页面
            loadLoadingPage();

            m_hasInitialize = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public View createView(Activity activity) {
        Intent intent = activity.getIntent();
        String url = intent.getStringExtra("url");
        if (url == null)
            url = m_config.getIndex();
        if (url.indexOf("http") != 0)
            url = "http://" + m_config.getDomain() + "/" + url;
        return new MyWebView(activity, url, m_loadingPage);
    }
}
