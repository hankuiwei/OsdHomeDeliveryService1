package com.lenovo.csd.eservice.http;

import com.lenovo.csd.eservice.entity.base.LoginData;
import com.lenovo.csd.eservice.http.callback.adapter.JsonHttpCallBack;
import com.lenovo.csd.eservice.http.callback.adapter.StringHttpCallBack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by shengtao
 * Date:2016/7/21
 * Time:11:03
 * 网络请求的demo，请求网络或者图片，可以参照。
 * 注意：
 * 1、回调的onSuccess和onError已经是UI线程
 * 2、JsonHttpCallBack,解析前的model类，字段相比服务器端json字符串，可多可少。
 * 如果model类多了响应字段，则该字段为null；如果少了某些字段，也不影响解析
 */
public class Demo {

    /**
     * get请求，callBack回调是字符串（StringHttpCallBack回调的是String，JSONHttpCallBack回调的是model）
     */
    public void getWithStringCallBack() {
        String mToken = "TzhHPS2fpzXpnouOlDraXMUYQJE9xzOKLuQp7LjJuLzf9YGpkCNQk7tpne19XOEoCTUu2MMBpDdER2UP";
        HttpClientManager.get("http://10.118.0.130:8100/api/logout/c47018?token=" + mToken, new StringHttpCallBack() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * get请求，callBack回调已经转换为model
     */
    public void getWithModelCallBack() {
        String mToken = "TzhHPS2fpzXpnouOlDraXMUYQJE9xzOKLuQp7LjJuLzf9YGpkCNQk7tpne19XOEoCTUu2MMBpDdER2UP";
        HttpClientManager.get("http://10.118.0.130:8100/api/logout/c47018?token=" + mToken, new JsonHttpCallBack<LoginData>() {

            @Override
            public void onSuccess(LoginData result) {

            }

            @Override
            public void onError(Exception e) {
            }
        });
    }

    /**
     * post请求，callBack回调是字符串，适用于Json字段比较简单的情况，比如：只关心json中code的种类
     */

    public void postWithStringCallBack() {

        Map<String, String> params = new HashMap<String, String>();
        params.put("user_code", "c47018");
        params.put("password", "1234qwer");
        params.put("mei_id", "xyz");
        params.put("app_version", "1.0.0");
        params.put("machine_name", "联想100");
        params.put("client_id", "d2d7d419e75e9e51e9c86c5ece0bfc8f");


        HttpClientManager.post("http://10.118.0.130:8100/api/login", params, new StringHttpCallBack() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    /**
     * post请求，callBack回调已经转换为model，适用于Json字段比较多的情况
     */
    public void postWithModelCallBack() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_code", "c47018");
        params.put("password", "1234qwer");
        params.put("mei_id", "xyz");
        params.put("app_version", "1.0.0");
        params.put("machine_name", "联想100");
        params.put("client_id", "d2d7d419e75e9e51e9c86c5ece0bfc8f");
        HttpClientManager.post("http://10.118.0.130:8100/api/login", params, new JsonHttpCallBack<LoginData>() {
            @Override
            public void onSuccess(LoginData result) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
